package com.example.zeroengineeringassignment.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zeroengineeringassignment.data.db.entities.TaskEntity
import com.example.zeroengineeringassignment.domain.uimodels.TaskItemUIModel
import com.example.zeroengineeringassignment.domain.usecases.AddUseCase
import com.example.zeroengineeringassignment.ui.state.AddTaskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val addUseCase: AddUseCase): ViewModel() {

    private val _addTaskState = MutableStateFlow(AddTaskUIState.Initial)
    val addTaskState = _addTaskState.asStateFlow()

    fun createTask(task: TaskEntity) {
        viewModelScope.launch {
            addUseCase.saveTask(task)
        }
    }

}