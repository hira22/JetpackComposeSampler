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

/**
 * Data Layer
 */
@Serializable
data class Task(
    @SerialName("userId")
    val userID: Long,

    val id: Long,
    val title: String,
    val completed: Boolean
)

interface TaskRepository {
    suspend fun fetchTasks(): List<Task>
}

class TaskRepositoryImpl : TaskRepository {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun fetchTasks(): List<Task> {
        val response = client.get("https://jsonplaceholder.typicode.com/todos")
        if (response.status != OK) {
            throw Exception("Failed to fetch todos")
        }
        delay(1000)
        return response.body()
    }
}

/**
 * Logic Layer
 */
data class TaskListState(
    val tasks: List<Task>,
    val isLoading: Boolean,
    val isError: Boolean,
    val toggleCompleted: (Task) -> Unit,
    val confirmError: () -> Unit
)

@Composable
fun rememberTaskListState(repository: TaskRepository): TaskListState {
    var tasks by remember { mutableStateOf(emptyList<Task>()) }
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        try {
            tasks = repository.fetchTasks()
        } catch (e: Exception) {
            isError = true
        } finally {
            isLoading = false
        }
    }

    return remember(tasks, isLoading, isError) {
        TaskListState(
            tasks = tasks,
            isLoading = isLoading,
            isError = isError,
            toggleCompleted = {
                tasks = tasks.map { task ->
                    if (task.id == it.id) {
                        task.copy(completed = !task.completed)
                    } else {
                        task
                    }
                }
            },
            confirmError = { isError = false }
        )
    }
}

/**
 * Presentation Layer
 */
@Composable
fun TaskListSample() {
    val state = rememberTaskListState(TaskRepositoryImpl())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Task List")
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
            LazyColumn(
                content = {
                    items(state.tasks) { task ->
                        Row {
                            Checkbox(
                                checked = task.completed,
                                onCheckedChange = {
                                    state.toggleCompleted(task)
                                }
                            )
                            Text(
                                text = task.title,
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
            if (state.isLoading) {
                CircularProgressIndicator(
                )
            }
        }
        if (state.isError) {
            AlertDialog(
                onDismissRequest = { state.confirmError() },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text(text = "Failed to fetch todos")
                },
                confirmButton = {
                    Button(onClick = { state.confirmError() }) {
                        Text(text = "OK")
                    }
                })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListSamplePreview() {
    TaskListSample()
}