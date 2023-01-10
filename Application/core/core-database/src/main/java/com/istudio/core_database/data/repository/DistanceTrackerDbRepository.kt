package com.istudio.core_database.data.repository

import com.demo.core_models.DistanceTrackerConstants
import com.istudio.core_database.data.implementation.DistanceTrackerConstantsDaoImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class DistanceTrackerDbRepository @Inject constructor(
    private val toDoDaoImpl: DistanceTrackerConstantsDaoImpl
) {

    suspend fun deleteAllTasks() {
        toDoDaoImpl.deleteTrackerConstants()
    }

    suspend fun insertTrackerConstants(distTrackerConst: DistanceTrackerConstants) {
        toDoDaoImpl.insertTrackerConstants(distTrackerConst)
    }

    fun getAllConstants(): Flow<DistanceTrackerConstants> {
        return toDoDaoImpl.getAllConstants()
    }

}
