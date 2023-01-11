package com.istudio.distancetracker.features.permission.presentation.state

sealed class PermissionStates {
    //object OnSubmitClick : MapStates()
    //data class ShowErrorMessage(val message: UiText) : PermissionStates()
    object NavigateToMapsScreen : PermissionStates()
    object RuntimeLocationPermission : PermissionStates()
}
