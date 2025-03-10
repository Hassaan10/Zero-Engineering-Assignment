package com.example.zeroengineeringassignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.zeroengineeringassignment.data.db.converters.Converters
import com.example.zeroengineeringassignment.data.db.dao.TaskDao
import com.example.zeroengineeringassignment.data.db.entities.TaskEntity
const val DB_VERSION = 1

@Database(entities = [TaskEntity::class], version = DB_VERSION)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}