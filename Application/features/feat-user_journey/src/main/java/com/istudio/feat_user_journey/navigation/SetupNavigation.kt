package com.istudio.feat_user_journey.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.istudio.feat_user_journey.navigation.ScreenKeys.LIST_SCREEN
import com.istudio.feat_user_journey.navigation.destinations.listComposable

@Composable
fun SetUpNavigation(
    navController : NavHostController
) {
    // We use remember because all screens need to use same instance , saving the back-stack
    val screen = remember(navController) { Screens(navController = navController) }
    
    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {
        // Inside the nav host we define the composable screens
        listComposable(
            navigateToTaskScreen = screen.task
        )
    }

}