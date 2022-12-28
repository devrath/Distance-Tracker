package com.istudio.distancetracker

import android.app.Application
import com.istudio.core_logger.ApplicationLoggerConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TrackerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            // New logger
            ApplicationLoggerConfig(this).initializeLogging(isEnabled = true)
        }
    }
}