package com.example.zeroengineeringassignment.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zeroengineeringassignment.data.db.entities.TaskEntity
import com.example.zeroengineeringassignment.data.models.Priority
import com.example.zeroengineeringassignment.data.models.Status
import com.example.zeroengineeringassignment.ui.viewmodels.AddViewModel
import com.example.zeroengineeringassignment.ui.widgets.TaskDatePicker
import com.example.zeroengineeringassignment.utils.convertLongToTime
import com.example.zeroengineeringassignment.utils.convertTimeToLong
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(viewModel: AddViewModel, onTaskAdded:() -> Unit) {
    val items = listOf(Priority.LOW.name, Priority.MEDIUM.name, Priority.HIGH.name)
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }

    var titleError by remember { mutableStateOf<String?>(null) }
    var dateError by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    fun validateTitle() {
        titleError = if (title.isBlank()) "Title cannot be empty" else null
    }

    fun validateDate() {
        dateError = if (date.isBlank()) "Date cannot be empty" else null
    }

    var selectedIndex by rememberSaveable { mutableIntStateOf(-1) }

    if (openDialog.value) {
        TaskDatePicker(onDismiss = {
            openDialog.value = false
        }, onSuccess = {
            openDialog.value = false
            it?.let {
                date = convertLongToTime(it)
                validateDate()
            }
        })
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Create Task") }
        )
    },snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = TopCenter
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                OutlinedTextField(
                    value = title,
                    isError = titleError != null,
                    onValueChange = {
                        title = it
                        validateTitle() },
                    label = { Text("Title") })

                titleError?.let {
                    Text(
                        text = titleError!!,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                }

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") })

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = date,
                        isError = dateError != null,
                        onValueChange = {},
                        label = { Text("Date") },
                        readOnly = true
                    )
                    IconButton(onClick = {
                        openDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date"
                        )
                    }
                }
                dateError?.let {
                    Text(
                        text = it,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                }


                LazyRow {
                    items(items.size) { index ->
                        FilterChip(
                            modifier = Modifier.padding(8.dp),
                            label = { Text(items[index])},
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index }
                        )
                    }
                }

                if (selectedIndex == -1) {
                    Text(
                        text = "Please select priority",
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                }

                Button(onClick = {
                        val task = TaskEntity(
                            title = title,
                            description = description,
                            date = convertTimeToLong(date),
                            priority = items[selectedIndex],
                            status = Status.PENDING.name
                        )
                        viewModel.createTask(task)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Task created successfully",
                        )
                    }
                    onTaskAdded()


                }, enabled = title.isNotEmpty()  && date.isNotEmpty() && selectedIndex != -1) {
                    Text(text = "Create Task")
                }
            }
        }
    }
}


@Preview
@Composable
fun CreateTaskScreenPreview() {
//    CreateTaskScreen(viewModel = AddViewModel())
}

