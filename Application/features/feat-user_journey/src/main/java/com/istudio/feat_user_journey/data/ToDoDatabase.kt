package com.istudio.feat_user_journey.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.istudio.feat_user_journey.data.models.ToDoTask

@Database(
    entities = [ToDoTask::class],
    version = 1,
    exportSchema = false)
abstract class ToDoDatabase : RoomDatabase(){
        abstract fun toDoDao() : ToDoDao
}