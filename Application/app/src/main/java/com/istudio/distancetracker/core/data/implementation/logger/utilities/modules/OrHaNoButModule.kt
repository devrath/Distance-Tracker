package com.istudio.distancetracker.core.data.implementation.logger.utilities.modules

import android.content.Context
import com.istudio.distancetracker.core.data.implementation.logger.utilities.adapters.AndroidDiskLogAdapter
import com.istudio.distancetracker.core.data.implementation.logger.utilities.adapters.AndroidLogCatAdapter
import com.orhanobut.logger.Logger

class OrHaNoButModule {

    fun initialize(context: Context) {
        // Log to be displayed in LogCat
        Logger.addLogAdapter(AndroidLogCatAdapter().initiate())
        // Log to be saved in the disk
        Logger.addLogAdapter(AndroidDiskLogAdapter().initiate(context))
    }
}
