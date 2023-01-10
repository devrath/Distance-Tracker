package com.demo.feat_repository

import com.demo.core_models.DistanceTrackerConstants
import com.demo.core_network.api.DistanceTrackerApi
import com.istudio.core_database.data.repository.DistanceTrackerDbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DistanceTrackerRepository @Inject constructor(
    private val api : DistanceTrackerApi,
    private val repository : DistanceTrackerDbRepository
) {

    suspend fun getConstantsFromDb(){
        val response = repository.getAllConstants()
    }

    suspend fun getConstantsFromApi(): DistanceTrackerConstants {
        return api.getConstants()
    }


}