package com.istudio.feat_user_journey.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.istudio.core_ui.composeUi.theme.DistanceTrackerTheme
import com.istudio.feat_user_journey.navigation.SetUpNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserJourneyActivity : ComponentActivity() {

    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DistanceTrackerTheme {
                navHostController = rememberNavController()
                SetUpNavigation(navController = navHostController)
            }
        }
    }
}
