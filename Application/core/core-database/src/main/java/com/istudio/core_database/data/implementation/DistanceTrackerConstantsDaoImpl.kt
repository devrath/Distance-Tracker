package com.istudio.core_database.data.implementation

import com.demo.core_models.DistanceTrackerConstants
import com.istudio.core_database.domain.dao.DistanceTrackerConstantsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DistanceTrackerConstantsDaoImpl @Inject constructor(
    private val dao: DistanceTrackerConstantsDao
) : DistanceTrackerConstantsDao {

    override suspend fun insertTrackerConstants(distTrackerConst: DistanceTrackerConstants) {
        dao.insertTrackerConstants(distTrackerConst)
    }

    override suspend fun insertTrackerConstantsWithTransaction(distTrackerConst: DistanceTrackerConstants) {
        super.insertTrackerConstantsWithTransaction(distTrackerConst)
    }

    override suspend fun deleteTrackerConstants() {
        dao.deleteTrackerConstants()
    }

    override fun getAllConstants(): Flow<DistanceTrackerConstants> {
        return dao.getAllConstants()
    }
}