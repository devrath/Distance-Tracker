package com.istudio.distancetracker.features.permission.presentation.vm

import androidx.lifecycle.viewModelScope
import com.demo.core_permission.domain.PermissionFeature
import com.istudio.core_common.base.BaseViewModel
import com.istudio.core_logger.domain.LoggerFeature
import com.istudio.distancetracker.features.permission.presentation.state.PermissionStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
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


    override fun onCleared() {
        super.onCleared()
        _eventChannel.cancel()
    }


}
