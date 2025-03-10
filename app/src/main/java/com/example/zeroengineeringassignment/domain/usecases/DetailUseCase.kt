package com.example.zeroengineeringassignment.domain.usecases

import com.example.zeroengineeringassignment.data.repository.TaskRepository
import javax.inject.Inject

class DetailUseCase @Inject constructor(private val repository: TaskRepository) {

    suspend fun fetchTask(id: Int) = repository.getTaskById(id)

    suspend fun markAsDone(id: Int) = repository.markTaskDone(id)
}