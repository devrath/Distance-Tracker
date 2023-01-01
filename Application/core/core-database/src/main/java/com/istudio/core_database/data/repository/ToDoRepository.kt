package com.istudio.core_database.data.repository

import com.istudio.core_database.data.implementation.ToDoDaoImpl
import com.istudio.core_database.models.ToDoTask
import kotlinx.coroutines.flow.Flow


open class ToDoRepository(
    private val toDoDaoImpl: ToDoDaoImpl
) {
    fun getAllTasks(): Flow<List<ToDoTask>> {
        return toDoDaoImpl.getAllTasks()
    }

    fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return toDoDaoImpl.getSelectedTask(taskId)
    }

    suspend fun addTask(task: ToDoTask) {
        toDoDaoImpl.addTask(task)
    }

    suspend fun updateTask(task: ToDoTask) {
        toDoDaoImpl.updateTask(task)
    }

    suspend fun deleteTask(task: ToDoTask) {
        toDoDaoImpl.deleteTask(task)
    }

    suspend fun deleteAllTasks() {
        toDoDaoImpl.deleteAllTasks()
    }

    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> {
        return toDoDaoImpl.searchDatabase(searchQuery)
    }

    fun sortByLowPriority(): Flow<List<ToDoTask>> {
        return toDoDaoImpl.sortByLowPriority()
    }

    fun sortByHighPriority(): Flow<List<ToDoTask>> {
        return toDoDaoImpl.sortByHighPriority()
    }
}
