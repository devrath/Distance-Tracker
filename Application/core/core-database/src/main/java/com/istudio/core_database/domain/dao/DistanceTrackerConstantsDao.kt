package com.istudio.core_database.domain.dao

import com.demo.core_models.DistanceTrackerConstants
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

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


    @Transaction
    suspend fun insertTrackerConstantsWithTransaction(distTrackerConst :DistanceTrackerConstants){
        deleteTrackerConstants()
        insertTrackerConstants(distTrackerConst)
    }

    /**
     * DESCRIPTION: Deleting the table
     */
    @Query("DELETE FROM distance_tracker_constants")
    suspend fun deleteTrackerConstants()

    /**
     * DESCRIPTION: Getting all the constants from the table
     */
    @Query("SELECT * FROM distance_tracker_constants")
    fun getAllConstants(): Flow<DistanceTrackerConstants>

}