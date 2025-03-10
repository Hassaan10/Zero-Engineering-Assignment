package com.example.zeroengineeringassignment.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zeroengineeringassignment.data.models.Priority
import com.example.zeroengineeringassignment.data.models.Status
import com.example.zeroengineeringassignment.ui.state.HomeUIState
import com.example.zeroengineeringassignment.ui.viewmodels.HomeViewModel
import com.example.zeroengineeringassignment.ui.widgets.TaskList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, onFabClick: () -> Unit, onItemClick: (id: Int) -> Unit) {

    val uiState by viewModel.homeState.collectAsStateWithLifecycle()

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    val filters = listOf(Status.ALL, Status.PENDING, Status.COMPLETED)

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = {
                Row {
                    Text(text = "Showing all your tasks")
                }
            })
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { padding ->

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                LazyRow {
                    items(filters.size) { index ->
                        FilterChip(
                            modifier = Modifier.padding(8.dp),
                            label = { Text(filters[index].name) },
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                viewModel.filterTask(filters[index])
                            }
                        )
                    }
                }

                when(uiState) {
                    is HomeUIState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is HomeUIState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Text(
                                text = (uiState as HomeUIState.Error).message,
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                    }

                    is HomeUIState.Success -> {
                        val tasks = (uiState as HomeUIState.Success).data

                        TaskList(tasks,
                            itemClick = onItemClick,
                            onStartClick = {
                                viewModel.markAsDone(it)
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Task marked as completed",
                                    )
                                }
                                           },
                            onEndClick = { viewModel.removeTask(it) },
                            onPositionChanged = { from, to ->
                                viewModel.moveTask(from, to)
                            })
                    }
                }
            }
        },

        floatingActionButton = {
            FloatingActionButton(onClick = onFabClick) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Add task")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
}