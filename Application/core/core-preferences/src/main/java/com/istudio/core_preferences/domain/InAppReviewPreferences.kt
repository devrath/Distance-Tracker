package com.istudio.core_preferences.domain

import kotlinx.coroutines.flow.Flow

/**
 * Provides the preferences that store if the user reviewed the app already, or not.
 *
 * It also stores info if the user chose to review the app later or never.
 *
 * If the user chose to never review the app - we don't prompt them with the review flow or dialog.
 * If the user chose to review the app later - we check if enough time has passed (e.g. a week).
 * If the user already chose to review the app, we shouldn't show the dialog or prompt again.
 * */
interface InAppReviewPreferences {

    /**
     * @return If the user has chosen the "Rate App" option or not.
     * */
    suspend fun hasUserRatedApp(): Flow<Boolean>

    /**
     * Stores if the user chose to rate the app or not.
     *
     * @param hasRated - If the user chose the "Rate App" option.
     * */
    suspend fun setUserRatedApp(hasRated: Boolean)


    /**
     * @return If the user has chosen the "Ask Me Later" option or not.
     * */
    suspend fun hasUserChosenRateLater(): Flow<Boolean>

    /**
     * Stores if the user wants to rate the app later.
     *
     * @param hasChosenRateLater - If the user chose the "Ask Me Later" option.
     * */
    suspend fun setUserChosenRateLater(hasChosenRateLater: Boolean)

    /**
     * @return Timestamp when the user chose the "Ask Me Later" option.
     * */
    suspend fun getRateLaterTime(): Flow<Long>


    /**
     * Stores the time when the user chose to review the app later.
     *
     * @param time - The timestamp when the user chose the "Ask Me Later" option.
     * */
    suspend fun setRateLater(time: Long)


    /**
     * @return The number of times the distance been tracked
     * */
    suspend fun noOfDistanceTracked(): Flow<Int>


    /**
     * Stores the number of times the distance been tracked.
     *
     * @param number - set the number of times
     * */
    suspend fun setNoOfDistanceTracked(number: Int)

    /**
     * Clears out the preferences.
     *
     * Useful for situations where we add some crucial features to the app and want to ask the user
     * for an opinion.
     *
     * This should be used only if the user didn't rate the app before.
     * */
    suspend fun clearIfUserDidNotRate()
}