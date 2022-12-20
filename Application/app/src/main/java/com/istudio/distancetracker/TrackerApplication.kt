package com.istudio.distancetracker

import android.app.Application
import com.istudio.distancetracker.core.data.implementation.logger.utilities.AppLoggerConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TrackerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            AppLoggerConfig(this).initializeLogging()
        }
    }
}