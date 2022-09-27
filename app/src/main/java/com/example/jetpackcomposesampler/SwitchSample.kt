package com.example.jetpackcomposesampler

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SimpleSwitchSample() {
    var checked by remember { mutableStateOf(false) }
    Switch(checked = checked, onCheckedChange = { checked = it })
}

@Preview(showBackground = true)
@Composable
fun SwitchSamplePreview() {
    SimpleSwitchSample()
}

@Composable
fun SwitchWithLabelSample() {
    var checked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.width(160.dp)
    ) {
        Text(
            text = "Label",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = { checked = it })
    }
}

@Preview(showBackground = true)
@Composable
fun SwitchWithLabelSamplePreview() {
    SwitchWithLabelSample()
}