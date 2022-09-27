package com.example.jetpackcomposesampler

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SimpleSliderSample() {
    var sliderPosition by remember { mutableStateOf(0f) }
    Column() {
        Text(text = "Slider value: ${sliderPosition.toInt()}")

        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f,
            steps = 9
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SliderSamplePreview() {
    SimpleSliderSample()
}