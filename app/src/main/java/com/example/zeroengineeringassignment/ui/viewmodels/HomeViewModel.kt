package com.example.zeroengineeringassignment.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zeroengineeringassignment.data.models.Status
import com.example.zeroengineeringassignment.domain.uimodels.TaskItemUIData
import com.example.zeroengineeringassignment.domain.uimodels.TaskItemUIModel
import com.example.zeroengineeringassignment.domain.usecases.DetailUseCase
import com.example.zeroengineeringassignment.domain.usecases.HomeUseCase
import com.example.zeroengineeringassignment.ui.state.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: HomeUseCase, private val detailUseCase: DetailUseCase): ViewModel() {

    private val _homeState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val homeState = _homeState.asStateFlow()

    private var tasks = listOf<TaskItemUIModel>()

    init {
        viewModelScope.launch {
            useCase.fetchAllTasks().onEach {
                tasks = it.map { item ->
                    TaskItemUIData(item)
                }.toMutableList()

                if (tasks.isNotEmpty()) {
                    _homeState.value = HomeUIState.Success(tasks)
                }
                else {
                    _homeState.value = HomeUIState.Error("No tasks found")
                }
            }.launchIn(viewModelScope)
        }
    }

//    fun moveUp(index: Int) {
//        if (index > 0) {
//            tasks.add(index - 1, tasks.removeAt(index))
//            _homeState.update { HomeUIState.Success(tasks) }
//        }
//    }
//
//    fun moveDown(index: Int) {
//        if (index < tasks.size - 1) {
//            tasks.add(index + 1, tasks.removeAt(index))
//            _homeState.update { HomeUIState.Success(tasks) }
//        }
//    }

    fun removeTask(index: Int) {
        tasks = tasks.toMutableList().apply {
            removeAt(index)
        }
        _homeState.update {
            HomeUIState.Success(tasks)
        }
    }

    fun moveTask(from:Int, to:Int) {

        tasks = tasks.toMutableList().apply {
            add(to, removeAt(from))
        }

        _homeState.update {  HomeUIState.Success(tasks) }
    }

    fun filterTask(status: Status) {
        val filteredList = when(status) {
            Status.PENDING -> tasks.filter { it.getStatus() == Status.PENDING.name }
            Status.COMPLETED -> tasks.filter { it.getStatus() == Status.COMPLETED.name }
            Status.ALL -> tasks
        }
        _homeState.update { HomeUIState.Success(filteredList) }
    }

    fun markAsDone(id: Int) {
        viewModelScope.launch {
            detailUseCase.markAsDone(id)
        }
    }
}