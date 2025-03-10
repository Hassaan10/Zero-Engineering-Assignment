package com.example.zeroengineeringassignment.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zeroengineeringassignment.domain.uimodels.TaskItemUIData
import com.example.zeroengineeringassignment.domain.uimodels.TaskItemUIModel
import com.example.zeroengineeringassignment.domain.usecases.DetailUseCase
import com.example.zeroengineeringassignment.domain.usecases.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val useCase: DetailUseCase): ViewModel() {

    private val _detailState = MutableStateFlow<TaskItemUIModel?>(null)
    val detailState = _detailState.asStateFlow()

    fun fetchTask(id:Int) {
        viewModelScope.launch {
            useCase.fetchTask(id).collect {
                _detailState.value = TaskItemUIData(it)
            }
        }
    }

    fun markAsDone(id: Int) {
        viewModelScope.launch {
            useCase.markAsDone(id)
        }
    }
}