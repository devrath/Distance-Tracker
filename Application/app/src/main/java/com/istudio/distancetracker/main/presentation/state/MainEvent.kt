package com.istudio.distancetracker.main.presentation.state

import com.demo.core_models.DistanceTrackerConstants
import kotlinx.coroutines.flow.Flow


sealed class MainEvent {
    data class ShowErrorMessage(val error: Throwable) : MainEvent()
    data class GetDistanceTrackerConstants(val constants: DistanceTrackerConstants) : MainEvent()
    object SplashSuccessful : MainEvent()
}
