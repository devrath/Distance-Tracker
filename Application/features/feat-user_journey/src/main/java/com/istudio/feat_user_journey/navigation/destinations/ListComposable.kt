package com.istudio.feat_user_journey.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.istudio.feat_user_journey.navigation.ScreenKeys.LIST_ARGUMENT_KEY
import com.istudio.feat_user_journey.navigation.ScreenKeys.LIST_SCREEN
import com.istudio.feat_user_journey.navigation.ScreenKeys.TASK_ARGUMENT_KEY
import com.istudio.feat_user_journey.screens.list.ListScreen

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit
){
    composable(
        route = LIST_SCREEN,
        arguments = listOf(
            navArgument(LIST_ARGUMENT_KEY){
                type = NavType.StringType
            })
    ){
        ListScreen(navigateToTaskScreen=navigateToTaskScreen)
    }
}