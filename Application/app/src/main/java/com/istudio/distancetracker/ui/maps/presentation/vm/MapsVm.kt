package com.istudio.distancetracker.ui.maps.presentation.vm

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.ButtCap
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
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

    companion object {
        const val widthValue = 10f
        const val colorValue = Color.BLUE
        const val typeValue = JointType.ROUND

        fun preparePolyline(locationList: MutableList<LatLng>): PolylineOptions {
            return PolylineOptions().apply {
                width(widthValue)
                color(colorValue)
                jointType(typeValue)
                startCap(ButtCap())
                endCap(ButtCap())
                addAll(locationList)
            }
        }
    }

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


    fun drawPolyline() {
        val polylineOptions = preparePolyline(locationList)
        viewModelScope.launch { _eventChannel.send(MapStates.AddPolyline(polylineOptions)) }
    }

    fun followPolyline() {
        if (locationList.isNotEmpty()) {
            val location = locationList.last()
            val duration = 1000
            viewModelScope.launch { _eventChannel.send(MapStates.FollowCurrentLocation(location,duration)) }
        }
    }

    fun addPolylineToList(polyLine: Polyline) {
        polylineList.add(polyLine)
    }


    @SuppressLint("MissingPermission")
    fun mapReset() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            val lastKnownLocation = LatLng(it.result.latitude, it.result.longitude)
            viewModelScope.launch { _eventChannel.send(MapStates.AnimateCamera(lastKnownLocation)) }
            viewModelScope.launch { _eventChannel.send(MapStates.DisplayStartButton) }
            resetViewModel()
        }
    }

    /**
     * Reset the states in the view model
     */
    fun resetViewModel() {
        for (polyLine in polylineList) { polyLine.remove() }
        for (marker in markerList) { marker.remove() }
        locationList.clear()
        markerList.clear()
    }

    /**
     * CALCULATE RESULT:
     */
    fun calculateResult() {
        log.i(KeysFeatureNames.FEATURE_MAP, "Calculate distance and result")
        val input = CalculateResultInput(locationData = locationList, startTime = startTime,stopTime = stopTime)
        useCases.calculateResult.invoke(input)
            .onSuccess { viewModelScope.launch { _eventChannel.send(MapStates.JourneyResult(it)) } }
            .onFailure { viewModelScope.launch { useCaseError(UseCaseResult.Error(Exception(it))) } }
    }

    /**
     * ERROR HANDLING: For the Use cases
     */
    private suspend fun useCaseError(result: UseCaseResult.Error) {
        log.e(KeysFeatureNames.FEATURE_MAP, result.exception.message.toString())
        val uiEvent = UiText.DynamicString(result.exception.message.toString())
        _eventChannel.send(MapStates.ShowErrorMessage(uiEvent))
    }

}
