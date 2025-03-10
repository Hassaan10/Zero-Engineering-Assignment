package com.example.zeroengineeringassignment.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedSelectionMenu(
    title: String,
    index: Int,
    options: List<String>,
    onSelected: (Int) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[index]) }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .width(200.dp)
            .padding(vertical = 4.dp),
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text(title) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                expanded = false

            }
        ) {
            options.forEachIndexed { index: Int, selectionOption: String ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onSelected(index)
                    },
                    text = { Text(text = selectionOption) }
                )
            }
        }
    }
}