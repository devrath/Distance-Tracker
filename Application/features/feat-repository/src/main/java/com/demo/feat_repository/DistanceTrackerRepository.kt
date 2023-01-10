package com.demo.feat_repository

import com.demo.core_models.DistanceTrackerConstants
import com.demo.core_network.api.DistanceTrackerApi
import com.istudio.core_common.functional.Resource
import com.istudio.core_common.functional.networkBoundResource
import com.istudio.core_database.data.repository.DistanceTrackerDbRepository
import com.istudio.core_database.database.DistanceTrackerDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DistanceTrackerRepository @Inject constructor(
    private val api: DistanceTrackerApi,
    private val database: DistanceTrackerDatabase
) {

    private val trackerDao = database.distanceTrackerConstantsDao()

    fun getConstantsFromApi(): Flow<Resource<DistanceTrackerConstants>> =
        networkBoundResource(
            shouldFetch = { true },
            query = {
                // Getting the data from database
                trackerDao.getAllConstants()
            },
            fetch = {
                // Getting the data from the API
                api.getConstants()
            },
            saveFetchResult = { trackerConstants ->
                // save the data in data base that is fetched from server
                trackerDao.insertTrackerConstantsWithTransaction(trackerConstants)
            }
        )
}