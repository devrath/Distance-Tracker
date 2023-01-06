package com.istudio.distancetracker.features.permission.presentation.vm

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.core_permission.domain.PermissionFeature
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
import com.istudio.core_ui.domain.SwitchUiModeFeature
import com.istudio.distancetracker.features.map.domain.MapFragmentUseCases
import com.istudio.distancetracker.features.map.domain.entities.inputs.CalculateResultInput
import com.istudio.distancetracker.features.map.presentation.state.MapStates
import com.istudio.distancetracker.Constants.FOLLOW_POLYLINE_UPDATE_DURATION
import com.istudio.distancetracker.Constants.preparePolyline
import com.istudio.distancetracker.features.KeysFeatureNames.FEATURE_MAP
import com.istudio.distancetracker.features.permission.presentation.state.PermissionStates
import com.istudio.feat_inappreview.InAppReviewView
import com.istudio.feat_inappreview.manager.InAppReviewManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionVm @Inject constructor(
    private var log: LoggerFeature,
    var permissionRepository: PermissionFeature
) : BaseViewModel() {

    /**
     * Using channel: We can notify the fragment to make fragment do something
     * Fragment should not be able to add values into the channel instead it should only be able to take value from the channel
     * Turning into the flow, the fragment can't put anything into it
     */
    private val _eventChannel = Channel<PermissionStates>()
    val events = _eventChannel.receiveAsFlow()


    fun initiateLocationFlow() {
        if (permissionRepository.hasLocationPermission()) {
            viewModelScope.launch {
                _eventChannel.send(PermissionStates.NavigateToMapsScreen)
            }
        } else {
            viewModelScope.launch {
                _eventChannel.send(PermissionStates.RuntimeLocationPermission)
            }
        }
    }



}
