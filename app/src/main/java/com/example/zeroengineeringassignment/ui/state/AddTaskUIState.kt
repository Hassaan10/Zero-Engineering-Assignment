package com.example.zeroengineeringassignment.ui.state

sealed class AddTaskUIState {
    data object Initial: AddTaskUIState()
    data class TitleError(val message: String): AddTaskUIState()
    data class DateError(val message: String): AddTaskUIState()
    data class PriorityError(val message: String): AddTaskUIState()
}