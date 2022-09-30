package com.example.jetpackcomposesampler

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SimpleTextSample() {
    Text(
        text = "Hello World!"
    )
}

@Preview(showBackground = true)
@Composable
fun SimpleTextSamplePreview() {
    SimpleTextSample()
}

@Composable
fun StyledTextSample() {
    Column() {
        Text(
            text = "Color Text",
            color = Color.Red
        )
        Text(
            text = "Font Size Text",
            fontSize = 20.sp
        )
        Text(
            text = "Bold",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "TextAlign.Center",
            textAlign = TextAlign.Center,
            modifier = Modifier.width(100.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StyledTextSamplePreview() {
    StyledTextSample()
}