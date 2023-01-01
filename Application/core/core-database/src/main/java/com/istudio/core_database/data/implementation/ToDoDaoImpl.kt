package com.istudio.core_database.data.implementation

import com.istudio.core_database.domain.dao.ToDoDao
import com.istudio.core_database.models.ToDoTask
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoDaoImpl @Inject constructor(
    private val toDoDao: ToDoDao
) : ToDoDao {

    override fun getAllTasks(): Flow<List<ToDoTask>> {
        return toDoDao.getAllTasks()
    }

    override fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return toDoDao.getSelectedTask(taskId)
    }

    override suspend fun addTask(task: ToDoTask) {
        toDoDao.addTask(task)
    }

    override suspend fun updateTask(task: ToDoTask) {
        toDoDao.updateTask(task)
    }

    override suspend fun deleteTask(task: ToDoTask) {
        toDoDao.deleteTask(task)
    }

    override suspend fun deleteAllTasks() {
        toDoDao.deleteAllTasks()
    }

    override fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> {
        return toDoDao.searchDatabase(searchQuery)
    }

    override fun sortByLowPriority(): Flow<List<ToDoTask>> {
        return toDoDao.sortByLowPriority()
    }

    override fun sortByHighPriority(): Flow<List<ToDoTask>> {
        return toDoDao.sortByHighPriority()
    }
}