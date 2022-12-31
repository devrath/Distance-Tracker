package com.istudio.feat_user_journey.data

import androidx.room.Dao
import androidx.room.Query
import com.istudio.feat_user_journey.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

/**
 * DESCRIPTION: We mention all the queries related to a table in the DAO interface class
 * The class has abstract methods, each representing a query each
 */
@Dao
interface ToDoDao {

    /**
     * DESCRIPTION: Select all the values from the table mentioned and sort by ascending order of the ID
     * RESULT: List of tasks wrapped in a flow data stream
     */
    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTasks() : Flow<List<ToDoTask>>

    /**
     * DESCRIPTION: Get the single task from the list of tasks of to-do task table
     * RESULT: Single task is returned 
     */
    @Query("SELECT * FROM todo_table WHERE id=:taskId")
    fun getSelectedTask(taskId:Int) : Flow<ToDoTask>

}