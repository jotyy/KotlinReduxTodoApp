package top.jotyy.kotlinreduxtodo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.rekotlin.StoreSubscriber
import top.jotyy.kotlinreduxtodo.R
import top.jotyy.kotlinreduxtodo.actions.*
import top.jotyy.kotlinreduxtodo.databinding.ActivityTodoBinding
import top.jotyy.kotlinreduxtodo.models.Todo
import top.jotyy.kotlinreduxtodo.states.TodoState
import top.jotyy.kotlinreduxtodo.store

class TodoActivity : AppCompatActivity(), StoreSubscriber<TodoState?> {
    private lateinit var binding: ActivityTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        store.dispatch(InitialTodoList(arrayListOf()))
        mockDatas()
        binding.apply {
            // Add
            btnAdd.setOnClickListener {
                if (!etTodo.text.isNullOrEmpty()) {
                    store.dispatch(
                        AddTodo(etTodo.text.toString())
                    )
                    etTodo.text?.clear()
                }
            }
            // filter
            visibilityFilter.addOnButtonCheckedListener { _, checkedId, _ ->
                when (checkedId) {
                    R.id.btnAll -> store.dispatch(SetVisibilityFilter(VisibilityFilter.ShowAll))
                    R.id.btnActive -> store.dispatch(SetVisibilityFilter(VisibilityFilter.ShowActive))
                    R.id.btnCompleted -> store.dispatch(SetVisibilityFilter(VisibilityFilter.ShowCompleted))
                }
            }
        }
    }

    private fun mockDatas() {
        val mockList = arrayListOf(
            "Read Kotlin Action",
            "Learn Redux",
            "Use Redux in Kotlin",
            "Write a article about learned",
        )

        mockList.forEach { store.dispatch(AddTodo(it)) }
    }

    override fun newState(state: TodoState?) {
        state?.apply {
            setState(todoList, filter)
        }
    }

    private fun setState(list: List<Todo>, filter: VisibilityFilter) {
        val todoList = list.filter {
            when (filter) {
                VisibilityFilter.ShowAll -> true
                VisibilityFilter.ShowCompleted -> it.completed
                VisibilityFilter.ShowActive -> !it.completed
            }
        }
        val todoListAdapter = TodoListAdapter(todoList)
        binding.rvTodo.apply {
            layoutManager = LinearLayoutManager(this@TodoActivity)
            adapter = todoListAdapter
        }
        todoListAdapter.setOnItemCheckListener {
            store.dispatch(ToggleTodo(it.id))
        }
    }

    override fun onStart() {
        super.onStart()
        store.subscribe(this) {
            it.select { state ->
                state
            }
        }
    }

    override fun onStop() {
        super.onStop()
        store.unsubscribe(this)
    }
}
