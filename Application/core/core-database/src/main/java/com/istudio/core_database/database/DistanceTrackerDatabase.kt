package com.istudio.core_database.database

import DistanceTrackerConstants
import androidx.room.Database
import androidx.room.RoomDatabase
import com.istudio.core_database.domain.dao.DistanceTrackerConstantsDao


/**
 * Abstract class:: ---> Because room later generates the code for this class
 * Why not make it Interface:: ---> This class need to extend the room-database and interface cannot extend the class so we make class abstract
 */
@Database(
    entities = [ DistanceTrackerConstants::class ],
    version = 1
)
abstract class DistanceTrackerDatabase : RoomDatabase() {
    abstract fun distanceTrackerConstantsDao() : DistanceTrackerConstantsDao
}