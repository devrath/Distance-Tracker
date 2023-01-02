package com.istudio.feat_user_journey.navigation

import androidx.navigation.NavHostController
import com.istudio.feat_user_journey.navigation.ScreenKeys.LIST_SCREEN
import com.istudio.feat_user_journey.util.Action

class Screens(navController:NavHostController) {
    // Navigating from: Task Screen ---back--> List Screen
    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}"){
            // When we navigate from task-composable to list-composable, We remove the list-screen
            popUpTo(LIST_SCREEN) {
                inclusive = true
            }
        }
    }

    // Navigating from: List Screen ---to--> Task Screen
    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}