package com.istudio.core_ui.domain

import android.app.Activity


interface SwitchUiModeFeature {
    suspend fun toggleUiMode() : Int
    suspend fun isDarkMode() : Boolean
    suspend fun saveToggledUiMode()
    fun animateAndRestartApplication(activity : Activity)
}