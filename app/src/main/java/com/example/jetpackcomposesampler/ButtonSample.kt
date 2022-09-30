package com.example.jetpackcomposesampler

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SimpleButtonSample() {
    Button(
        onClick = { /*TODO*/ }
    ) {
        Text(text = "Button")

    }
}

@Preview(showBackground = true)
@Composable
fun ButtonSamplePreview() {
    SimpleButtonSample()
}

@Composable
fun ButtonSampleWithIcon() {
    Button(
        shape = RectangleShape,
        onClick = { /*TODO*/ }
    ) {
        Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
        Text(text = "Button")
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonSampleWithIconPreview() {
    ButtonSampleWithIcon()
}

@Composable
fun IconButtonSample() {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
    }
}

@Preview(showBackground = true)
@Composable
fun IconButtonSamplePreview() {
    IconButtonSample()
}

@Composable
fun OutlinedButtonSample() {
    OutlinedButton(
        onClick = { /*TODO*/ }
    ) {
        Text(text = "Button")
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedButtonSamplePreview() {
    OutlinedButtonSample()
}

@Composable
fun RadioButtonSample() {
    var state by remember { mutableStateOf(value = true) }
    Row(Modifier.selectableGroup()) {
        RadioButton(
            selected = state,
            onClick = { state = true }
        )
        RadioButton(
            selected = !state,
            onClick = { state = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RadioButtonSamplePreview() {
    RadioButtonSample()
}

@Composable
fun MultiSelectRadioButtonSample() {
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
                RadioButton(
                    selected = item.second,
                    onClick = { onItemClicked(item.first) }
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

// see also checkbox sample
@Preview(showBackground = true)
@Composable
fun MultiSelectRadioButtonSamplePreview() {
    MultiSelectRadioButtonSample()
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ButtonSampleScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Button Sample") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { _ ->
        LazyColumn {
            item {
                SimpleButtonSample()
            }
            item {
                ButtonSampleWithIcon()
            }
            item {
                IconButtonSample()
            }
            item {
                OutlinedButtonSample()
            }
            item {
                RadioButtonSample()
            }
//            item {
//                MultiSelectRadioButtonSample()
//            }
        }
    }
}