package com.istudio.distancetracker.features.permission.presentation.state

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.istudio.core_common.ui.uiEvent.UiText
import com.istudio.distancetracker.features.map.domain.entities.outputs.CalculateResultOutput

sealed class PermissionStates {
    //object OnSubmitClick : MapStates()
    //data class ShowErrorMessage(val message: UiText) : PermissionStates()
    object NavigateToMapsScreen : PermissionStates()
    object RuntimeLocationPermission : PermissionStates()
}
