package com.istudio.distancetracker.main.presentation.state


sealed class MainEvent {
    data class ShowErrorMessage(val error: Throwable) : MainEvent()
    data class GetTrackerConstantsApiCall(val isSuccess: Boolean) : MainEvent()
    object SplashSuccessful : MainEvent()
}
