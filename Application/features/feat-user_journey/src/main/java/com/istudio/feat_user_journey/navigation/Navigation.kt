package com.istudio.feat_user_journey.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.istudio.feat_user_journey.navigation.ScreenKeys.LIST_SCREEN
import com.istudio.feat_user_journey.navigation.destinations.listComposable
import com.istudio.feat_user_journey.navigation.destinations.taskComposable

@Composable
fun SetUpNavigation(
    navController : NavHostController
) {
    // Variable keeps track of all the composable states
    val screen = remember(navController) { Screens(navController = navController) }
    
    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {
        // <-------------- Define the navigation graph -------------->
        // LIST COMPOSABLE
        listComposable(navigateToTaskScreen = screen.task)
        // TASK COMPOSABLE
        taskComposable(navigateToListScreen = screen.list)
    }

}