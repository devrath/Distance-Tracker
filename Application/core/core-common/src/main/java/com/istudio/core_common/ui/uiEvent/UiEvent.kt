package com.istudio.core_common.ui.uiEvent

sealed class UiEvent {
    object Success : UiEvent()
    object NavigateUp : UiEvent()
    data class ShowSnackbar(val message: UiText) : UiEvent()
}
