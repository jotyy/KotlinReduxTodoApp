package top.jotyy.kotlinreduxtodo.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import top.jotyy.kotlinreduxtodo.databinding.ItemTodoBinding
import top.jotyy.kotlinreduxtodo.models.Todo

class TodoListAdapter(
    private val list: List<Todo>
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {
    private var itemCheckListener: ((Todo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemTodoBinding =
            ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemTodoBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun setOnItemCheckListener(listener: ((Todo) -> Unit)) {
        itemCheckListener = listener
    }

    inner class ViewHolder(
        private val itemTodoBinding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(itemTodoBinding.root) {
        fun bind(todo: Todo) {
            itemTodoBinding.cbCompleted.isChecked = todo.completed
            itemTodoBinding.cbCompleted.setOnCheckedChangeListener { _, _ ->
                itemCheckListener?.invoke(todo)
            }
            itemTodoBinding.tvContent.text = todo.content
            if (todo.completed) {
                itemTodoBinding.tvContent.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
    }
}
