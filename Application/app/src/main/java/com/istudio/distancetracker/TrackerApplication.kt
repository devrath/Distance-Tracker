package com.istudio.distancetracker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.istudio.core_logger.ApplicationLoggerConfig
import com.istudio.core_preferences.data.repository.PreferenceRepository
import com.istudio.core_ui.data.models.Mode
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class TrackerApplication: Application() {

    @Inject
    lateinit var preferences: PreferenceRepository

    override fun onCreate() {
        super.onCreate()
        setLogger()
        setCurrentUiMode()
    }

    /**
     * Set the logger for the application
     * *************
     * Description: This ensures logs are only printed in debug mode and in prod build the logs are not printed
     */
    private fun setLogger() {
        if (BuildConfig.DEBUG) {
            ApplicationLoggerConfig(this).initializeLogging(isEnabled = true)
        }
    }

    /**
     * Set the UI-MODE for the application for current session
     * *************
     * Description: This ensures logs are only printed in debug mode and in prod build the logs are not printed
     */
    private fun setCurrentUiMode() {
        with(ProcessLifecycleOwner.get()) {
            lifecycleScope.launch {
                val currentMode = when (preferences.getUiModeState().first()) {
                    Mode.LIGHT.ordinal -> AppCompatDelegate.MODE_NIGHT_NO
                    Mode.DARK.ordinal -> AppCompatDelegate.MODE_NIGHT_YES
                    //Mode.SYSTEM.ordinal -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
                AppCompatDelegate.setDefaultNightMode(currentMode)
            }
        }
    }
}