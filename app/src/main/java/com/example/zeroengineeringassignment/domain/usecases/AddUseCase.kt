package com.example.zeroengineeringassignment.domain.usecases

import com.example.zeroengineeringassignment.data.db.entities.TaskEntity
import com.example.zeroengineeringassignment.data.repository.TaskRepository
import javax.inject.Inject

class AddUseCase @Inject constructor(private val repository: TaskRepository) {

    suspend fun saveTask(taskEntity: TaskEntity) {
        repository.addTask(taskEntity)
    }

}