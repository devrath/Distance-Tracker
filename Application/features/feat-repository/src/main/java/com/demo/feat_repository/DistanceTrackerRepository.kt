package com.demo.feat_repository

import com.demo.core_models.DistanceTrackerConstants
import com.demo.core_network.api.DistanceTrackerApi
import com.istudio.core_database.data.repository.DistanceTrackerDbRepository
import com.istudio.core_database.database.DistanceTrackerDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DistanceTrackerRepository @Inject constructor(
    private val api : DistanceTrackerApi,
    private val repository : DistanceTrackerDbRepository,
    private val database : DistanceTrackerDatabase
) {

    suspend fun getConstantsFromDb(){
        val response = repository.getAllConstants()
    }

    suspend fun getConstantsFromApi(): DistanceTrackerConstants {
        return api.getConstants()
    }



}