package com.example.jetpackcomposesampler

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SimpleCheckBoxSample() {
    var checked by remember { mutableStateOf(false) }
    Checkbox(
        checked = checked,
        onCheckedChange = { checked = it }
    )
}


@Preview(showBackground = true)
@Composable
fun CheckBoxSamplePreview() {
    SimpleCheckBoxSample()
}

@Composable
fun MultiSelectCheckBoxSample() {
    var items by remember {
        mutableStateOf(
            (0..5).map { Pair(it, false) }
        )
    }

    val onItemClicked: (Int) -> Unit = { index ->
        items = items.mapIndexed { i, pair ->
            if (i == index) {
                Pair(pair.first, !pair.second)
            } else {
                pair
            }
        }
    }

    LazyColumn() {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .selectableGroup()
                    .clickable { onItemClicked(item.first) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = item.second,
                    onCheckedChange = { onItemClicked(item.first) }
                )
                Text(
                    text = " Item ${item.first}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MultiSelectCheckBoxSamplePreview() {
    MultiSelectCheckBoxSample()
}