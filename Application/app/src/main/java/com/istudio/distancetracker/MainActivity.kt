package com.istudio.distancetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.istudio.distancetracker.utils.Permissions

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Set nav controller
        setNavController()
        // Open the first screen
        openScreen()
    }

    /**
     * Set the nav controller for the screen
     */
    private fun setNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun openScreen() {
        if (Permissions.hasLocationPermission(this@MainActivity)) { navController.navigate(R.id.action_permissionFragment_to_mapFragment) }
    }

}