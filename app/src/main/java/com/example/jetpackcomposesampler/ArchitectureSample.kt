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
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Infrastructure layer
 */
class APIClient {
    companion object {
        val client = HttpClient {
            install(ContentNegotiation) {
                json()
            }
            defaultRequest {
                url("https://jsonplaceholder.typicode.com")
            }
        }
    }
}

/**
 * Data Layer
 */
@Serializable
data class Task(
    @SerialName("userId")
    val userID: Int,

    val id: Int,
    val title: String?,
    val completed: Boolean
)

@Serializable
data class Post(
    @SerialName("userId")
    val userID: Int,
    val id: Int,
    val title: String?,
    val body: String?
)

interface TaskRepository {
    suspend fun fetchTasks(): List<Task>
    suspend fun updatePost(task: Task): Post
}

class TaskRepositoryImpl : TaskRepository {
    override suspend fun fetchTasks(): List<Task> {
        val response = APIClient.client.get("todos")
        if (response.status != OK) {
            throw Exception("Failed to fetch todos")
        }
        delay(1000)
        return response.body()
    }

    override suspend fun updatePost(task: Task): Post {
        val response = APIClient.client.post("posts") {
            contentType(ContentType.Application.Json)
            setBody(Post(task.userID, task.id, task.title, "body"))
        }
        if (response.status != Created) {
            throw Exception("${response.status}: Failed to update post")
        }
        return response.body()
    }
}

/**
 * Logic Layer
 */
data class TaskListState(
    val tasks: List<Task>,
    val isLoading: Boolean,
    val errorMessage: String?,
    val toggleCompleted: (Task) -> Unit,
    val confirmError: () -> Unit
)

@Composable
fun rememberTaskListState(repository: TaskRepository): TaskListState {
    var tasks by remember { mutableStateOf(emptyList<Task>()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val composableScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        isLoading = true
        try {
            tasks = repository.fetchTasks()
        } catch (e: Exception) {
            errorMessage = e.message
        } finally {
            isLoading = false
        }
    }

    return remember(tasks, isLoading, errorMessage) {
        TaskListState(
            tasks = tasks,
            isLoading = isLoading,
            errorMessage = errorMessage,
            toggleCompleted = {

                composableScope.launch {
                    isLoading = true
                    errorMessage = try {
                        val post = repository.updatePost(it)
                        "Updated post: $post"
                    } catch (e: Exception) {
                        e.message
                    } finally {
                        isLoading = false
                    }
                }
                tasks = tasks.map { task ->
                    if (task.id == it.id) {
                        task.copy(completed = !task.completed)
                    } else {
                        task
                    }
                }
            },
            confirmError = { errorMessage = null }
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
                    items(state.tasks, key = { it.id }) { task ->
                        Row {
                            Checkbox(
                                checked = task.completed,
                                onCheckedChange = {

                                    state.toggleCompleted(task)
                                }
                            )
                            Text(
                                text = task.title ?: "",
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
                CircularProgressIndicator()
            }
        }
        if (state.errorMessage != null) {
            AlertDialog(
                onDismissRequest = { state.confirmError() },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text(text = state.errorMessage)
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