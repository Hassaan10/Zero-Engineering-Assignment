package com.example.zeroengineeringassignment.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String?,
    val priority: String,
    val status: String,
    val date: Long
)