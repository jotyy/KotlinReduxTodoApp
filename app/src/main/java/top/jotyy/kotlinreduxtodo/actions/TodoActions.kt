package top.jotyy.kotlinreduxtodo.actions

import org.rekotlin.Action
import top.jotyy.kotlinreduxtodo.models.Todo

class InitialTodoList(val todos: List<Todo>) : Action

class AddTodo(val todoContent: String) : Action

class ToggleTodo(val todoId: Int) : Action

class SetVisibilityFilter(val filter: VisibilityFilter) : Action

sealed class VisibilityFilter {
    object ShowAll : VisibilityFilter()
    object ShowCompleted : VisibilityFilter()
    object ShowActive : VisibilityFilter()
}
