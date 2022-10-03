package com.example.jetpackcomposesampler

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend fun main() {

    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    try {
        val response = client.get("https://jsonplaceholder.typicode.com/todos")
        if (response.status != OK) {
            throw Exception("Failed to fetch todos")
        }
        val todos: List<Todo> = response.body()
        println(todos.first())
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        client.close()
    }
}

@Serializable
data class Todo(
    @SerialName("userId")
    val userID: Long,

    val id: Long,
    val title: String,
    val completed: Boolean
)

@Composable
fun TodoListSample() {
    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }
    var todos by remember { mutableStateOf(listOf<Todo>()) }
    var isError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            isLoading = true
            val response = client.get("https://jsonplaceholder.typicode.com/todos")
            if (response.status != OK) {
                throw Exception("Failed to fetch todos")
            }
            val fetchedTodos: List<Todo> = response.body()
            delay(1000)
            todos = fetchedTodos
        } catch (e: Exception) {
            e.printStackTrace()
            isError = true
        } finally {
            client.close()
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Todo List")
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (isError) {
                Text(
                    text = "Error",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    content = {
                        items(todos) { todo ->
                            Row {
                                Checkbox(
                                    checked = todo.completed,
                                    onCheckedChange = {
                                        todos = todos.map { t ->
                                            if (t.id == todo.id) {
                                                t.copy(completed = it)
                                            } else {
                                                t
                                            }
                                        }
                                    }
                                )
                                Text(
                                    text = todo.title,
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(Alignment.CenterVertically),
                                )
                            }
                            Divider()
                        }
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            if (isLoading) {
                CircularProgressIndicator(
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListSamplePreview() {
    TodoListSample()
}
