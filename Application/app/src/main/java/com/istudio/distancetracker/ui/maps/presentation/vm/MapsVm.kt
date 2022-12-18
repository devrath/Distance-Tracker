package com.istudio.distancetracker.ui.maps.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
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
