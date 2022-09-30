package com.example.jetpackcomposesampler

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ColumnSample() {
    Column {
        Text("Hello")
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(color = Color.Red)
        )
        Text("World")
    }
}

@Preview(showBackground = true)
@Composable
fun ColumnSamplePreview() {
    ColumnSample()
}

@Composable
fun RowSample() {
    Row {
        Text("Hello")
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color = Color.Red)
        )
        Text("World")
    }
}

@Preview(showBackground = true)
@Composable
fun RowSamplePreview() {
    RowSample()
}

@Composable
fun BoxSample() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.White)
    ) {
        Text("Hello")
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color = Color.Red)
        )
        Text("World", modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Preview(showBackground = true)
@Composable
fun BoxSamplePreview() {
    BoxSample()
}