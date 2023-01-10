package com.istudio.core_database.data.repository

import DistanceTrackerConstants
import com.istudio.core_database.data.implementation.DistanceTrackerConstantsDaoImpl
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
open class DistanceTrackerRepository(
    private val toDoDaoImpl: DistanceTrackerConstantsDaoImpl
) {

    suspend fun deleteAllTasks() {
        toDoDaoImpl.deleteTrackerConstants()
    }

    suspend fun insertTrackerConstants(distTrackerConst: DistanceTrackerConstants) {
        toDoDaoImpl.insertTrackerConstants(distTrackerConst)
    }

}
