package com.example.zeroengineeringassignment.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.zeroengineeringassignment.data.db.entities.TaskEntity
import com.example.zeroengineeringassignment.data.models.Status
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    fun getAllTasks() : Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    fun getTaskByID(id: Int) : Flow<TaskEntity>

    @Query("UPDATE TaskEntity SET status = :status WHERE id = :id")
    suspend fun markTaskDone(id: Int, status: String = Status.COMPLETED.name)
}