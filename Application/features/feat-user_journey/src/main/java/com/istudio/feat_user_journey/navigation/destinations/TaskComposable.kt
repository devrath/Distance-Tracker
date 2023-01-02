package com.istudio.feat_user_journey.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.istudio.feat_user_journey.navigation.ScreenKeys
import com.istudio.feat_user_journey.util.Action

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit
){
    composable(
        route = ScreenKeys.LIST_SCREEN,
        arguments = listOf(
            navArgument(ScreenKeys.TASK_ARGUMENT_KEY){
                type = NavType.IntType
            })
    ){

    }
}