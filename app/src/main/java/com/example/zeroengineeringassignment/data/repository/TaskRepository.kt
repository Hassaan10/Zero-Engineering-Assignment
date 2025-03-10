package com.example.zeroengineeringassignment.data.repository

import com.example.zeroengineeringassignment.data.db.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    suspend fun getAllTasks(): Flow<List<TaskEntity>>
    suspend fun getTaskById(id: Int): Flow<TaskEntity>
    suspend fun markTaskDone(id: Int)
}