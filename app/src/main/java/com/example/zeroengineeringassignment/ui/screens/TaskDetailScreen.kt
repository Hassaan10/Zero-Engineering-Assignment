package com.example.zeroengineeringassignment.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.zeroengineeringassignment.ui.viewmodels.DetailViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(viewModel: DetailViewModel, id:Int, onTaskAdded:() -> Unit) {

    val task by viewModel.detailState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = id) {
        viewModel.fetchTask(id)
    }

    Scaffold(topBar = {
        TopAppBar(
        title = {Text("Task Detail")},
    )
    }, snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
        Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            task?.let {
                Card(colors = CardDefaults.cardColors().copy(
                    containerColor = it.getBackgroundColor(),
                    contentColor = Color.White
                )) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Title: " + it.getTitle(),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        it.getDescription()?.let { description ->
                            Text(
                                text = "Description: $description",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = "Date: " + it.getDueDate(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        ElevatedButton(onClick = {
                            viewModel.markAsDone(it.getID())
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Task Completed successfully",
                                )
                                onTaskAdded()
                            }
                        }) {
                            Text(text = "Mark As Done")
                        }
                    }
                }
            }
        }
    }
}