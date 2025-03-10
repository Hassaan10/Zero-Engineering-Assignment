package com.example.zeroengineeringassignment.ui.widgets

import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDatePicker(onDismiss:() ->  Unit, onSuccess:(date:Long?) -> Unit) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled =
        remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onSuccess(datePickerState.selectedDateMillis)
            }, enabled = confirmEnabled.value) { Text("OK") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss)
            { Text("Cancel") }
        }) { DatePicker(state = datePickerState) }
}