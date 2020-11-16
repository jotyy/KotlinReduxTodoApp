package top.jotyy.kotlinreduxtodo.reducers

import org.rekotlin.Action
import top.jotyy.kotlinreduxtodo.actions.*
import top.jotyy.kotlinreduxtodo.models.Todo
import top.jotyy.kotlinreduxtodo.states.TodoState

fun todoReducer(action: Action, todoState: TodoState?): TodoState {
    val state = todoState ?: TodoState()
    return when (action) {
        is InitialTodoList -> return state
        is AddTodo -> return state.copy(
            todoList = state.todoList.toMutableList().apply {
                add(
                    Todo(
                        id = this.lastIndex + 1,
                        content = action.todoContent
                    )
                )
            }
        )
        is ToggleTodo -> {
            return state.copy(
                todoList = state.todoList.map { todo ->
                    if (action.todoId == todo.id) {
                        todo.copy(completed = !todo.completed)
                    } else {
                        todo
                    }
                })
        }
        is SetVisibilityFilter -> {
            state.copy(
                filter = action.filter
            )
        }
        else -> TodoState()
    }
}
