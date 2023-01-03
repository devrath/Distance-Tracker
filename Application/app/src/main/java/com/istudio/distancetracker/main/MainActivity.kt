package com.istudio.distancetracker.main

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.common.IntentSenderForResultStarter
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import com.istudio.core_common.extensions.toast
import com.istudio.core_common.navigation.NavigationUtils
import com.istudio.distancetracker.Constants.APP_UPDATE_REQUEST_CODE
import com.istudio.distancetracker.Constants.APP_UPDATE_TYPE
import com.istudio.distancetracker.R
import com.istudio.distancetracker.databinding.ActivityMainBinding
import com.istudio.distancetracker.features.permission.utils.Permissions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    // ********************************** Life cycle methods ***************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavController()
        checkForUpdates()
        //openScreen()
    }

    override fun recreate() {
        animateForUiModeChange()
    }
    // ********************************** Life cycle methods ***************************************

    // ********************************** User defined functions ************************************
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
        if (Permissions.hasLocationPermission(this@MainActivity)) {
            NavigationUtils.navigateSafe(navController, R.id.action_permissionFragment_to_mapFragment, null);
        }
    }

    /**
     * This lets user to recreate the activity via animation when we switch the UI-modes
     */
    private fun animateForUiModeChange() {
        finish()
        val fadeIn = com.istudio.core_ui.R.anim.fade_in
        val fadeOut = com.istudio.core_ui.R.anim.fade_out
        overridePendingTransition(fadeIn, fadeOut);
        startActivity(intent);
        overridePendingTransition(fadeIn, fadeOut);
    }
    // ********************************** User defined functions ************************************

    /** ******************************** APPLICATION UPDATE  *********************************** **/
    private fun checkForUpdates() {
        val appUpdateManager = AppUpdateManagerFactory.create(baseContext)
        val appUpdateInfo = appUpdateManager.appUpdateInfo
        appUpdateInfo.addOnSuccessListener {
            handleUpdate(appUpdateManager, appUpdateInfo)
        }
    }

    private fun handleUpdate(manager: AppUpdateManager, info: Task<AppUpdateInfo>) {

        /* ************************************************************************************
        * The update availability can be one of the following values
        * DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS: When there’s an ongoing update.
        * UPDATE_AVAILABLE: When a new update is available.
        * UPDATE_NOT_AVAILABLE: When there’s no update available.
        * UNKNOWN: When there was a problem connecting to the app store.
        * *************************************************************************************/

        if(info.result.updateAvailability() == UpdateAvailability.UPDATE_NOT_AVAILABLE){
            // Start normal app flow: -> Open the app
            openScreen()
        }else if ( // Before starting this process, verify that there’s an update available or one already in progress.
            (info.result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE ||
                info.result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                )
            // Verify if the immediate type is supported.
            && info.result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
        ) {
            // Start or resume the update with the startUpdateFlowForResult but only if the previous conditions are true.
            startUpdate(info.result,manager)
        }
    }

    /**
     * Prepare and start the update Screen intent
     */
    private fun startUpdate(
        appUpdateInfo: AppUpdateInfo, appUpdateManager: AppUpdateManager,
    ) {
        val starter =
            IntentSenderForResultStarter { intent, _, fillInIntent, flagsMask, flagsValues, _, _ ->
                val request = IntentSenderRequest.Builder(intent)
                    .setFillInIntent(fillInIntent)
                    .setFlags(flagsValues, flagsMask)
                    .build()

                appUpdateLauncher.launch(request)
            }

        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo, APP_UPDATE_TYPE, starter, APP_UPDATE_REQUEST_CODE,
        )
    }

    private var appUpdateLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { activityResult ->
            activityResult?.let {
                when (activityResult.resultCode) {
                    Activity.RESULT_OK -> toast(R.string.toast_updated)
                    Activity.RESULT_CANCELED -> toast(R.string.toast_cancelled)
                    ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> toast(R.string.toast_failed)
                }
            }
        }
    /** ******************************** APPLICATION UPDATE  *********************************** **/
}