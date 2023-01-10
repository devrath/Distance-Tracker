package com.istudio.distancetracker.main

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.demo.core_permission.domain.PermissionFeature
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.common.IntentSenderForResultStarter
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import com.istudio.core_common.extensions.exhaustive
import com.istudio.core_common.extensions.toast
import com.istudio.core_common.functional.Resource
import com.istudio.core_common.navigation.NavigationUtils
import com.istudio.core_ui.domain.SwitchUiModeFeature
import com.istudio.distancetracker.Constants.APP_UPDATE_REQUEST_CODE
import com.istudio.distancetracker.Constants.APP_UPDATE_TYPE
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.ActivityMainBinding
import com.istudio.distancetracker.main.presentation.state.MainEvent
import com.istudio.distancetracker.main.presentation.vm.MainVm
import com.istudio.distancetracker.service.InAppUpdate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel: MainVm by viewModels()

    private lateinit var inAppUpdate: InAppUpdate

    @Inject
    lateinit var permissionFeature: PermissionFeature

    @Inject
    lateinit var switchUiModeFeature: SwitchUiModeFeature

    // ********************************** Life cycle methods ***************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        initOnCreate()
    }

    override fun onResume() {
        super.onResume()
        initOnResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        initOnDestroy()
    }

    override fun recreate() {
       initOnRecreate()
    }
    // ********************************** Life cycle methods ***************************************

    // ********************************** View states **********************************************
    private fun observeViewStates() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { event ->
                when(event){
                    is MainEvent.ShowErrorMessage -> displayUserMessage(event.error.message)
                    is MainEvent.SplashSuccessful -> displayUserMessage("Data synced")
                    is MainEvent.GetTrackerConstantsApiCall ->  viewModel.constantsSynched()
                }
            }.exhaustive
        }
    }
    // ********************************** View states **********************************************

    // ********************************** User defined functions ************************************

    /**
     * Init function for onCreate
     */
    private fun initOnCreate() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Set the navigation controller
        setNavController()
        // Set observables
        observeViewStates()
        // Initiate update
        inAppUpdate = InAppUpdate(this)
        // Trigger the api for synching the constants in the application
        viewModel.getConstantsFromApplication()
        // Open the screen
        openScreen()
    }

    /**
     * Init function for onResume
     */
    private fun initOnResume() {
        inAppUpdate.onResume()
    }

    /**
     * Init function for onDestroy
     */
    private fun initOnDestroy() {
        inAppUpdate.onDestroy()
    }

    /**
     * Init function for onRecreate
     */
    private fun initOnRecreate() {
        // This lets user to recreate the activity via animation when we switch the UI-modes
        switchUiModeFeature.animateAndRestartApplication(this)
    }

    /**
     * Set the nav controller for the screen
     */
    private fun setNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    /**
     * Open the Map screen
     */
    private fun openScreen() {
        if (permissionFeature.hasLocationPermission()) {
            NavigationUtils.navigateSafe(navController, R.id.action_permissionFragment_to_mapFragment, null);
        }
    }

    /**
     * Display user the error messages
     */
    private fun displayUserMessage(message: String?) {
        message?.let { Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show(); }
    }
    // ********************************** User defined functions ************************************
}