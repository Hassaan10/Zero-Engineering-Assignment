package com.example.zeroengineeringassignment.ui.state

import com.example.zeroengineeringassignment.domain.uimodels.TaskItemUIModel

sealed class HomeUIState {
    data object Loading: HomeUIState()
    data class Success(val data: List<TaskItemUIModel>): HomeUIState()
    data class Error(val message: String): HomeUIState()
}