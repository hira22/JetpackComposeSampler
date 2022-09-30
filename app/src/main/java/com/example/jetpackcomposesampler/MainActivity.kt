package com.example.jetpackcomposesampler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposesampler.ui.theme.JetpackComposeSamplerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeSamplerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()
                    JetpackComposeSamplerApp(navController = navController)
                }
            }
        }
    }
}

@Composable
fun JetpackComposeSamplerApp(navController: NavHostController) {
    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("button") {
            ButtonSampleScreen(navController = navController)
        }
        composable("text") {
            SimpleTextSample()
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    val items = listOf(
        "Button" to "button",
        "Text" to "text",
    )
    LazyColumn {
        items(items) { (title, destination) ->
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(destination) }
            )
        }
    }
}