package com.istudio.core_database.data.implementation

import DistanceTrackerConstants
import com.istudio.core_database.domain.dao.DistanceTrackerConstantsDao
import javax.inject.Inject

class DistanceTrackerConstantsDaoImpl @Inject constructor(
    private val dao: DistanceTrackerConstantsDao
) : DistanceTrackerConstantsDao {

    override suspend fun insertTrackerConstants(distTrackerConst: DistanceTrackerConstants) {
        dao.insertTrackerConstants(distTrackerConst)
    }

    override suspend fun deleteTrackerConstants() {
        dao.deleteTrackerConstants()
    }

}