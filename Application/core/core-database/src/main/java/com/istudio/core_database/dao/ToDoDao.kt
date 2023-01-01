package com.istudio.core_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

    /**
     * DESCRIPTION: Using the insert command we add a task to the list of tasks in database
     * STRATEGY: If there is a value that already exists, then just ignore it
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task : ToDoTask)

    /**
     * DESCRIPTION: Updating a task in the list of tasks
     */
    @Update
    suspend fun updateTask(task:ToDoTask)

    /**
     * DESCRIPTION: Deleting a task from the list of tasks
     */
    @Delete
    suspend fun deleteTask(task:ToDoTask)

    /**
     * DESCRIPTION: Updating a task in the list of tasks
     */
    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTasks()

    /**
     * DESCRIPTION: Select all the values from the table whose title and description match the searchQuery passed
     * RESULT: It will return a list zero or more tasks matching the query
     */
    @Query("SELECT * FROM todo_table " +
        "WHERE title LIKE :searchQuery " +
        "OR " +
        "description LIKE :searchQuery")
    fun searchDatabase(searchQuery:String) : Flow<List<ToDoTask>>


    @Query("SELECT * FROM todo_table " +
        "ORDER BY " +
        "CASE " +
        "WHEN priority LIKE 'L%' THEN 1 " +
        "WHEN priority LIKE 'M%' THEN 2 " +
        "WHEN priority LIKE '%H' THEN 3 " +
        "END")
    fun sortByLowPriority(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table " +
        "ORDER BY " +
        "CASE " +
        "WHEN priority LIKE 'H%' THEN 1 " +
        "WHEN priority LIKE 'M%' THEN 2 " +
        "WHEN priority LIKE '%L' THEN 3 " +
        "END")
    fun sortByHighPriority(): Flow<List<ToDoTask>>

}