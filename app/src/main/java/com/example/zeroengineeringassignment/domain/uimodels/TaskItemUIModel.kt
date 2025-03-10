package com.example.zeroengineeringassignment.domain.uimodels

import androidx.compose.ui.graphics.Color
import com.example.zeroengineeringassignment.data.db.entities.TaskEntity
import com.example.zeroengineeringassignment.data.models.Priority
import com.example.zeroengineeringassignment.utils.convertLongToTime

interface TaskItemUIModel {
    fun getID(): Int
    fun getTitle(): String
    fun getDescription(): String?
    fun getBackgroundColor(): Color
    fun getStatus(): String
    fun getDueDate(): String
    fun getEntity(): TaskEntity

}

data class TaskItemUIData(val task: TaskEntity) : TaskItemUIModel {

    override fun getID(): Int {
        return task.id
    }

    override fun getTitle(): String {
        return task.title
    }

    override fun getDescription(): String? {
        return task.description
    }

    override fun getBackgroundColor(): Color {
        return when(Priority.valueOf(task.priority)) {
            Priority.HIGH -> Color.Red
            Priority.MEDIUM -> Color.Yellow
            Priority.LOW -> Color.Blue
        }
    }

    override fun getStatus(): String {
        return task.status
    }

    override fun getDueDate(): String {
        return convertLongToTime(task.date)
    }

    override fun getEntity(): TaskEntity {
        return TaskEntity(
            id = task.id,
            title = task.title,
            description = task.description,
            priority = task.priority,
            status = task.status,
            date = task.date
        )
    }
}