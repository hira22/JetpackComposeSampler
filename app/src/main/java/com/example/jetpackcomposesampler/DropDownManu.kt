package com.example.jetpackcomposesampler

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SimpleDropDownMenuSample() {
    var expanded by remember { mutableStateOf(false) }
    var selectedValue: String? by remember { mutableStateOf(null) }

    val options = listOf("Option 1", "Option 2", "Option 3")
    Box(modifier = Modifier.size(200.dp)) {
        Column() {
            Text(
                text = "Selected value : ${selectedValue ?: "None"}",
            )
            Button(onClick = { expanded = true }) {
                Text("Show menu")
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    selectedValue = option
                    expanded = false
                }) {
                    Text(text = option)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownMenuSamplePreview() {
    SimpleDropDownMenuSample()
}