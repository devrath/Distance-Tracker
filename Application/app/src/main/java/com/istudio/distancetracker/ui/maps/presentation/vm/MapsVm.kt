package com.istudio.distancetracker.ui.maps.presentation.vm

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.ButtCap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.istudio.distancetracker.core.domain.features.logger.LoggerFeature
import com.istudio.distancetracker.core.platform.base.BaseViewModel
import com.istudio.distancetracker.core.platform.functional.UseCaseResult
import com.istudio.distancetracker.core.platform.ui.uiEvent.UiText
import com.istudio.distancetracker.features.KeysFeatureNames
import com.istudio.distancetracker.features.map.domain.MapFragmentUseCases
import com.istudio.distancetracker.features.map.domain.entities.inputs.CalculateResultInput
import com.istudio.distancetracker.ui.maps.presentation.state.MapStates
import com.istudio.distancetracker.utils.Constants.FOLLOW_POLYLINE_UPDATE_DURATION
import com.istudio.distancetracker.utils.Constants.preparePolyline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsVm @Inject constructor(
    private var useCases: MapFragmentUseCases,
    private var fusedLocationProviderClient: FusedLocationProviderClient,
    private var log: LoggerFeature,
) : BaseViewModel() {

    /**
     * Using channel: We can notify the fragment to make fragment do something
     * Fragment should not be able to add values into the channel instead it should only be able to take value from the channel
     * Turning into the flow, the fragment can't put anything into it
     */
    private val _eventChannel = Channel<MapStates>()
    val events = _eventChannel.receiveAsFlow()


    var startTime = 0L
    var stopTime = 0L

    val started = MutableLiveData(false)

    var locationList = mutableListOf<LatLng>()
    var polylineList = mutableListOf<Polyline>()
    var markerList = mutableListOf<Marker>()



    private fun drawPolyline() {
        val polylineOptions = preparePolyline(locationList)
        viewModelScope.launch { _eventChannel.send(MapStates.AddPolyline(polylineOptions)) }
    }

    private fun followPolyline() {
        if (locationList.isNotEmpty()) {
            val location = locationList.last()
            val duration = FOLLOW_POLYLINE_UPDATE_DURATION
            viewModelScope.launch { _eventChannel.send(MapStates.FollowCurrentLocation(location,duration)) }
        }
    }

    fun addPolylineToList(polyLine: Polyline) {
        polylineList.add(polyLine)
    }


    @SuppressLint("MissingPermission")
    fun mapReset() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { location ->
            location.result?.let { latLngLoc ->
                val lastKnownLocation = LatLng(latLngLoc.latitude, latLngLoc.longitude)
                viewModelScope.launch { _eventChannel.send(MapStates.AnimateCamera(lastKnownLocation)) }
                viewModelScope.launch { _eventChannel.send(MapStates.DisplayStartButton) }
                resetViewModel()
            }
        }
    }

    /**
     * Reset the states in the view model
     */
    private fun resetViewModel() {
        for (polyLine in polylineList) { polyLine.remove() }
        for (marker in markerList) { marker.remove() }
        locationList.clear()
        markerList.clear()
    }

    /**
     * CALCULATE RESULT
     */
    private fun calculateResult() {
        log.i(KeysFeatureNames.FEATURE_MAP, "Calculate distance and result")
        val input = CalculateResultInput(locationData = locationList, startTime = startTime,stopTime = stopTime)
        useCases.calculateResult.invoke(input)
            .onSuccess { viewModelScope.launch { _eventChannel.send(MapStates.JourneyResult(it)) } }
            .onFailure { viewModelScope.launch { useCaseError(UseCaseResult.Error(Exception(it))) } }
    }

    fun addMarker(marker: Marker?) { marker?.let { markerList.add(it) } }

    private fun showBiggerPicture() {
        val padding = 100
        val duration = 2000
        val bounds = LatLngBounds.Builder()
        for (location in locationList) {
            bounds.include(location)
        }
        viewModelScope.launch {
            _eventChannel.send(MapStates.AnimateCameraForBiggerPitchure(
                bounds = bounds.build(), padding = padding, duration = duration
            ))
        }
        viewModelScope.launch { _eventChannel.send(MapStates.AddMarker(locationList.first())) }
        viewModelScope.launch { _eventChannel.send(MapStates.AddMarker(locationList.last())) }
    }

    // ********************************* Service-States ********************************************
    fun trackerServiceInProgress(locations: MutableList<LatLng>?) {
        locations?.let {
            locationList = it
            Log.d("LocationReceived",it.toString())
            if (locationList.size > 1) {
                // Giving the user the option to stop the service
                viewModelScope.launch { _eventChannel.send(MapStates.DisableStopButton) }
            }
            // On each call-back, it will draw the list of points in the mutable list
            drawPolyline()
            // As when the polyline is drawn, camera has to follow the points of the mutable list
            followPolyline()
        }
    }

    fun trackerStartedState(state: Boolean?) { state?.let { started.value = state } }

    fun trackerStartTime(time: Long?) { time?.let { startTime = it } }

    fun trackerStopTime(time: Long?) {
        time?.let {
            stopTime = it
            if (stopTime != 0L) {
                if (locationList.isNotEmpty()) {
                    showBiggerPicture()
                    calculateResult()
                }
            }
        }
    }
    // ********************************* Service-States ********************************************

    /**
     * ERROR HANDLING: For the Use cases
     */
    private suspend fun useCaseError(result: UseCaseResult.Error) {
        log.e(KeysFeatureNames.FEATURE_MAP, result.exception.message.toString())
        val uiEvent = UiText.DynamicString(result.exception.message.toString())
        _eventChannel.send(MapStates.ShowErrorMessage(uiEvent))
    }

}
