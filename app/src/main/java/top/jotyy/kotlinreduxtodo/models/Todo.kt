package top.jotyy.kotlinreduxtodo.models

data class Todo(
    val id: Int,
    val content: String,
    var completed: Boolean = false
)
