package com.istudio.distancetracker.main.presentation.state

sealed class MainEvent {
    data class ShowErrorMessage(val error: Throwable) : MainEvent()
    object SplashSuccessful : MainEvent()
}
