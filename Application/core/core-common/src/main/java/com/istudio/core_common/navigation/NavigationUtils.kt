package com.istudio.core_common.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph

/**
 * SOURCE: https://stackoverflow.com/a/72878419/1083093
 */
object NavigationUtils {
    /**
     * This function will check navigation safety before starting navigation using direction
     *
     * @param navController NavController instance
     * @param direction     navigation operation
     */
    fun navigateSafe(navController: NavController, direction: NavDirections) {
        val currentDestination = navController.currentDestination
        if (currentDestination != null) {
            val navAction = currentDestination.getAction(direction.actionId)
            if (navAction != null) {
                val destinationId = orEmpty(navAction.destinationId)
                val currentNode: NavGraph? = if (currentDestination is NavGraph) currentDestination else currentDestination.parent
                if (destinationId != 0 && currentNode != null && currentNode.findNode(destinationId) != null) {
                    navController.navigate(direction)
                }
            }
        }
    }

    /**
     * This function will check navigation safety before starting navigation using resId and args bundle
     *
     * @param navController NavController instance
     * @param resId         destination resource id
     * @param args          bundle args
     */
    fun navigateSafe(navController: NavController, @IdRes resId: Int, args: Bundle?) {
        val currentDestination = navController.currentDestination
        if (currentDestination != null) {
            val navAction = currentDestination.getAction(resId)
            if (navAction != null) {
                val destinationId = orEmpty(navAction.destinationId)
                val currentNode: NavGraph? = if (currentDestination is NavGraph) currentDestination else currentDestination.parent
                if (destinationId != 0 && currentNode != null && currentNode.findNode(destinationId) != null) {
                    navController.navigate(resId, args)
                }
            }
        }
    }

    private fun orEmpty(value: Int?): Int {
        return value ?: 0
    }
}