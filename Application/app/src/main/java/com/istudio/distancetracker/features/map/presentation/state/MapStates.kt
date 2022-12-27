package com.istudio.distancetracker.features.map.presentation.state

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.istudio.distancetracker.core.platform.ui.uiEvent.UiText
import com.istudio.distancetracker.features.map.domain.entities.outputs.CalculateResultOutput
import com.istudio.distancetracker.features.map.presentation.vm.MapsVm

sealed class MapStates {
    //object OnSubmitClick : MapStates()
    data class ShowErrorMessage(val message: UiText) : MapStates()
    data class JourneyResult(val result: CalculateResultOutput) : MapStates()
    data class AnimateCamera(val location: LatLng) : MapStates()
    data class AnimateCameraForBiggerPitchure(val bounds: LatLngBounds, val padding:Int, val duration:Int) : MapStates()

    data class AddPolyline(val polyLine: PolylineOptions) : MapStates()
    data class FollowCurrentLocation(val location: LatLng, val duration:Int) : MapStates()
    data class AddMarker(val location: LatLng) : MapStates()

    object DisplayStartButton : MapStates()
    object DisableStopButton : MapStates()

    object LaunchInAppReview : MapStates()
}
