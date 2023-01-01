package com.istudio.core_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.istudio.core_database.dao.ToDoDao
import com.istudio.core_database.models.ToDoTask

@Database(
    entities = [ToDoTask::class],
    version = 1,
    exportSchema = false)
abstract class ToDoDatabase : RoomDatabase(){
        abstract fun toDoDao() : ToDoDao
}