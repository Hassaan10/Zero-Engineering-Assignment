package com.example.zeroengineeringassignment.domain.usecases

import com.example.zeroengineeringassignment.data.db.entities.TaskEntity
import com.example.zeroengineeringassignment.data.models.Priority
import com.example.zeroengineeringassignment.data.models.Status
import com.example.zeroengineeringassignment.data.repository.TaskRepository
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val repository: TaskRepository) {

    suspend fun fetchAllTasks() = repository.getAllTasks()

}