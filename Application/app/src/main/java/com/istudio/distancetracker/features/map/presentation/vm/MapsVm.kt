package com.istudio.distancetracker.features.map.presentation.vm

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.istudio.core_common.base.BaseViewModel
import com.istudio.core_common.functional.UseCaseResult
import com.istudio.core_common.ui.uiEvent.UiText
import com.istudio.core_location.domain.LastLocationFeature
import com.istudio.core_location.domain.LocationFeature
import com.istudio.core_logger.domain.LoggerFeature
import com.istudio.core_connectivity.domain.ConnectivityFeature
import com.istudio.core_preferences.domain.InAppReviewPreferences
import com.istudio.core_ui.toggleUiMode.Mode
import com.istudio.distancetracker.features.map.domain.MapFragmentUseCases
import com.istudio.distancetracker.features.map.domain.entities.inputs.CalculateResultInput
import com.istudio.distancetracker.features.map.presentation.state.MapStates
import com.istudio.distancetracker.Constants.FOLLOW_POLYLINE_UPDATE_DURATION
import com.istudio.distancetracker.Constants.preparePolyline
import com.istudio.distancetracker.features.KeysFeatureNames.FEATURE_MAP
import com.istudio.feat_inappreview.InAppReviewView
import com.istudio.feat_inappreview.manager.InAppReviewManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsVm @Inject constructor(
    private var useCases: MapFragmentUseCases,
    private var locationFeature: LocationFeature,
    private var connectivity: ConnectivityFeature,
    private var log: LoggerFeature,
    private var lastLocationFeature: LastLocationFeature,
    // Feature: In-App Review
    private var reviewManager: InAppReviewManager,
    // Preferences used to update the rate app prompt flags.
    var preferences: InAppReviewPreferences,
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


    private lateinit var inAppReviewView: InAppReviewView


    private fun drawPolyline() {
        log.i(FEATURE_MAP, "Polyline draw is invoked")
        val polylineOptions = preparePolyline(locationList)
        viewModelScope.launch { _eventChannel.send(MapStates.AddPolyline(polylineOptions)) }
    }

    private fun followPolyline() {
        if (locationList.isNotEmpty()) {
            log.i(FEATURE_MAP, "Follow user as he travels <--> Current-Location:->$locationList.last()")
            val location = locationList.last()
            val duration = FOLLOW_POLYLINE_UPDATE_DURATION
            viewModelScope.launch { _eventChannel.send(MapStates.FollowCurrentLocation(location,duration)) }
        }
    }

    fun addPolylineToList(polyLine: Polyline) {
        log.i(FEATURE_MAP, "new polyline is added to list of polylines")
        polylineList.add(polyLine)
    }


    @SuppressLint("MissingPermission")
    fun mapReset() {
        viewModelScope.launch {
            lastLocationFeature.currentLocation().collect{ location ->
                val lastKnownLocation = LatLng(location.latitude, location.longitude)
                _eventChannel.send(MapStates.AnimateCamera(lastKnownLocation))
                _eventChannel.send(MapStates.DisplayStartButton)
                resetViewModel()
            }
        }
        showInAppReview()
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
        log.i(FEATURE_MAP, "Calculate distance and result")
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
            _eventChannel.send(
                MapStates.AnimateCameraForBiggerPitchure(
                bounds = bounds.build(), padding = padding, duration = duration
            ))
        }
        viewModelScope.launch { _eventChannel.send(MapStates.AddMarker(locationList.first())) }
        viewModelScope.launch { _eventChannel.send(MapStates.AddMarker(locationList.last())) }
    }

    fun checkLocationEnabled() = locationFeature.isLocationEnabled()

    fun checkConnectivity(): Boolean = connectivity.checkConnectivity()

    // ********************************* Review Prompt *********************************************
    private fun showInAppReview() {
        viewModelScope.launch {
            when {
                reviewManager.isEligibleForReview() -> {
                    viewModelScope.launch { _eventChannel.send(MapStates.LaunchInAppReview) }
                }
            }
        }
    }
    // ********************************* Review Prompt *********************************************

    // ********************************* Service-States ********************************************
    fun trackerServiceInProgress(locations: MutableList<LatLng>?) {
        locations?.let {
            locationList = it
            log.i(FEATURE_MAP, "LocationReceived:->$locationList")
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

    // ********************************* Preference-States *****************************************

    fun setFlagTrackerIsUsed() {
        viewModelScope.launch {
            var number = preferences.noOfDistanceTracked().first()
            number++
            preferences.setNoOfDistanceTracked(number)
        }
    }

    /**
     * This will return true if the current mode is dark mode
     * else if false in case the current mode is dark mode
     */
    suspend fun isDarkMode(): Boolean {
        val currentMode = preferences.getUiModeForApp().first()
        if (currentMode == Mode.LIGHT.ordinal){ return false }
        else { return true }
    }

    suspend fun toggleUiMode(): Int {
        return if(isDarkMode()){
            // Set light mode
            AppCompatDelegate.MODE_NIGHT_NO
        }else {
            // Set dark mode
            AppCompatDelegate.MODE_NIGHT_YES
        }
    }


    suspend fun saveToggledUiMode() {
        if(isDarkMode()){
            // Set light mode
            preferences.setUiModeForApp(Mode.LIGHT.ordinal)
        }else {
            // Set dark mode
            preferences.setUiModeForApp(Mode.DARK.ordinal)
        }
    }
    // ********************************* Preference-States *****************************************

    /**
     * ERROR HANDLING: For the Use cases
     */
    private suspend fun useCaseError(result: UseCaseResult.Error) {
        log.e(FEATURE_MAP, result.exception.message.toString())
        val uiEvent = UiText.DynamicString(result.exception.message.toString())
        _eventChannel.send(MapStates.ShowErrorMessage(uiEvent))
    }
}
