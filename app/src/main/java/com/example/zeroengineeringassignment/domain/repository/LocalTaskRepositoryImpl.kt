package com.example.zeroengineeringassignment.domain.repository

import com.example.zeroengineeringassignment.data.db.dao.TaskDao
import com.example.zeroengineeringassignment.data.db.entities.TaskEntity
import com.example.zeroengineeringassignment.data.repository.TaskRepository
import javax.inject.Inject

class LocalTaskRepositoryImpl @Inject constructor(private val dao: TaskDao): TaskRepository {

    override suspend fun addTask(task: TaskEntity) = dao.insertTask(task)

    override suspend fun deleteTask(task: TaskEntity) = dao.deleteTask(task)

    override suspend fun getAllTasks() = dao.getAllTasks()

    override suspend fun getTaskById(id: Int) = dao.getTaskByID(id)

    override suspend fun markTaskDone(id: Int) = dao.markTaskDone(id)
}