package com.istudio.distancetracker

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.istudio.core_logger.ApplicationLoggerConfig
import com.istudio.core_preferences.data.repository.PreferenceRepository
import com.istudio.core_ui.toggleUiMode.Mode
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
        if (BuildConfig.DEBUG) {
            // New logger
            ApplicationLoggerConfig(this).initializeLogging(isEnabled = true)
        }

        with(ProcessLifecycleOwner.get()) {
            lifecycleScope.launch {
                val prefValue = preferences.getUiModeState().first()

                val nightMode = when (prefValue) {
                    Mode.LIGHT.ordinal -> AppCompatDelegate.MODE_NIGHT_NO
                    Mode.DARK.ordinal -> AppCompatDelegate.MODE_NIGHT_YES
                    Mode.SYSTEM.ordinal -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    else -> AppCompatDelegate.MODE_NIGHT_NO
                }

                AppCompatDelegate.setDefaultNightMode(nightMode)
            }
        }
    }
}