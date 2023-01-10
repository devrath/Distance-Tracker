package com.istudio.core_database.domain.dao

import DistanceTrackerConstants
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * DESCRIPTION: We mention all the queries related to a table in the DAO interface class
 * The class has abstract methods, each representing a query each
 */
@Dao
interface DistanceTrackerConstantsDao {

    /**
     * DESCRIPTION: Using the insert command we add a entry to the table
     * STRATEGY: If there is a value that already exists, then just ignore it
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackerConstants(distTrackerConst : DistanceTrackerConstants)

    /**
     * DESCRIPTION: Deleting the table
     */
    @Query("DELETE FROM distance_tracker_constants")
    suspend fun deleteTrackerConstants()

}