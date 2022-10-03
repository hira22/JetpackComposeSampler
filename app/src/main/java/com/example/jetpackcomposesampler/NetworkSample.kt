package com.example.jetpackcomposesampler

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend fun main() {

    val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }
    val todo: Todo = client.get("https://jsonplaceholder.typicode.com/todos/1").body()
    println(todo)
}

@Serializable
data class Todo(
    @SerialName("userId")
    val userID: Long,

    val id: Long,
    val title: String,
    val completed: Boolean
)
