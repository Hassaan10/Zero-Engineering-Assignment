package com.example.zeroengineeringassignment.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.zeroengineeringassignment.ui.screens.CreateTaskScreen
import com.example.zeroengineeringassignment.ui.screens.HomeScreen
import com.example.zeroengineeringassignment.ui.screens.TaskDetailScreen
import com.example.zeroengineeringassignment.ui.viewmodels.AddViewModel
import com.example.zeroengineeringassignment.ui.viewmodels.DetailViewModel
import com.example.zeroengineeringassignment.ui.viewmodels.HomeViewModel
import com.example.zeroengineeringassignment.utils.Screen
import kotlinx.serialization.Serializable

@Serializable
data class Task(val id: Int)

@Composable
fun MainNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HOME.name ) {

        composable(Screen.HOME.name) {
            val homeViewModel = hiltViewModel<HomeViewModel>()

            HomeScreen(viewModel = homeViewModel, onFabClick = {
                navController.navigate(Screen.ADD.name)
            }, onItemClick = { id ->
                navController.navigate(Task(id))
            })
        }

        composable(Screen.ADD.name) {
            val addViewModel = hiltViewModel<AddViewModel>()
            CreateTaskScreen(addViewModel, onTaskAdded = {
                navController.popBackStack()
            })
        }

        composable<Task> { backStackEntry ->
            val task: Task = backStackEntry.toRoute()
            val detailViewModel = hiltViewModel<DetailViewModel>()
            TaskDetailScreen(detailViewModel, task.id, onTaskAdded = {
                navController.popBackStack()
            })
        }

    }

}