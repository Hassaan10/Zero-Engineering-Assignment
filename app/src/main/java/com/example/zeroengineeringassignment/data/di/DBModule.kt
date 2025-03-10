package com.example.zeroengineeringassignment.data.di

import android.content.Context
import androidx.room.Room
import com.example.zeroengineeringassignment.data.db.AppDatabase
import com.example.zeroengineeringassignment.data.db.dao.TaskDao
import com.example.zeroengineeringassignment.data.repository.TaskRepository
import com.example.zeroengineeringassignment.domain.repository.LocalTaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    fun provideDataBase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "task-db"
        ).build()
    }

    @Provides
    fun provideTaskDao(db: AppDatabase) = db.taskDao()

    @Provides
    fun bindTaskRepository(dao: TaskDao): TaskRepository {
        return LocalTaskRepositoryImpl(dao)
    }
}