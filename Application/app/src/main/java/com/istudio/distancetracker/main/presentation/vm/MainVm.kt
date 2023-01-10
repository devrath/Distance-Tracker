package com.istudio.distancetracker.main.presentation.vm

import com.istudio.core_common.base.BaseViewModel
import com.istudio.core_logger.domain.LoggerFeature
import com.istudio.distancetracker.main.presentation.state.MainEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MainVm @Inject constructor(
    private var log: LoggerFeature
) : BaseViewModel()  {

    /**
     * Activity should not be able to add values into the channel instead it should only be able to take value from the channel
     * Turning into the flow, the activity can't put anything into it
     */
    private val _eventChannel = Channel<MainEvent>()
    val events = _eventChannel.receiveAsFlow()


}