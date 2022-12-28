package com.istudio.core_logger

import android.content.Context
import timber.log.Timber


class ApplicationLoggerConfig(private val context: Context?) {

    companion object {
        var isLoggerEnabled = false
    }

    fun initializeLogging(isEnabled : Boolean) {
        context?.let {
           isLoggerEnabled = isEnabled
            if(isLoggerEnabled){
                Timber.plant(Timber.DebugTree())
            }
        }
    }
}
