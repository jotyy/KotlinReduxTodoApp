package top.jotyy.kotlinreduxtodo.states

import org.rekotlin.StateType
import top.jotyy.kotlinreduxtodo.actions.VisibilityFilter
import top.jotyy.kotlinreduxtodo.models.Todo

data class TodoState(
    var todoList: List<Todo> = arrayListOf(),
    var filter: VisibilityFilter = VisibilityFilter.ShowAll
) : StateType
