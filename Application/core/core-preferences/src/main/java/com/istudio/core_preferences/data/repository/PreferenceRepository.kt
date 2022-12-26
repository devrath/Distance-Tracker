package com.istudio.core_preferences.data.repository

import com.istudio.core_preferences.domain.InAppReviewPreferences
import com.istudio.core_preferences.domain.PreferenceDatastore
import kotlinx.coroutines.flow.Flow

class PreferenceRepository(private val preference: InAppReviewPreferences) {

    suspend fun saveOnBoardingState(hasRated: Boolean) {
        preference.setUserRatedApp(hasRated)
    }

    suspend fun hasUserRatedApp(): Flow<Boolean> {
        return preference.hasUserRatedApp()
    }

    suspend fun setUserChosenRateLater(hasChosenRateLater: Boolean) {
        preference.setUserChosenRateLater(hasChosenRateLater)
    }

    suspend fun hasUserChosenRateLater(): Flow<Boolean> {
        return preference.hasUserChosenRateLater()
    }

    suspend fun setRateLater(time: Long) {
        preference.setRateLater(time)
    }

    suspend fun getRateLaterTime(): Flow<Long> {
        return preference.getRateLaterTime()
    }
}
