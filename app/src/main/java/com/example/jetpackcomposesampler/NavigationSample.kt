package com.example.jetpackcomposesampler

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationSample() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "onboarding",
        modifier = Modifier.fillMaxSize()
    ) {
        composable("onboarding") {
            Column {
                Text("I am on onboarding")
                Button(onClick = {
                    navController.navigate("dashboard") {
                        popUpTo("dashboard") // I want to get rid of onboarding here
                    }
                }) {
                    Text("go to dashboard")
                }
            }
        }
        composable("dashboard") {
            Column {
                Text("I am on dashboard")
                Button(onClick = {
                    navController.navigate("detail")
                }) {
                    Text("go to detail")
                }
            }
        }
        composable("detail") {
            Text("I am on detail")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationSamplePreview() {
    NavigationSample()
}