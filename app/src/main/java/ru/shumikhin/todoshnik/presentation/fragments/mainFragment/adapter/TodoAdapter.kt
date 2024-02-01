package ru.shumikhin.todoshnik.presentation.fragments.mainFragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.shumikhin.todoshnik.databinding.TodoListItemBinding
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.utils.DateConverter

class TodoAdapter(
    private  val listener: TodoRecyclerEvent
): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    var todoList: List<TodoItem> = emptyList()
        set(newValue){
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TodoListItemBinding.inflate(inflater, parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        with(holder.binding) {
            tvTodoTitle.text = todo.text
            if (todo.deadline == null) {
                tvTodoDeadline.visibility = View.GONE
            } else {
                tvTodoDeadline.visibility = View.VISIBLE
                tvTodoDeadline.text = DateConverter.timestampToString(todo.deadline!!)
            }
            checkboxIsCompleted.isChecked = todo.isCompleted
        }
    }

    inner class TodoViewHolder(val binding : TodoListItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.btnTodoInfo.setOnClickListener(this)
            binding.root.setOnClickListener(this)

            binding.checkboxIsCompleted.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onCheckBoxClick(position, binding.checkboxIsCompleted.isChecked)
                }
            }
        }
        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface TodoRecyclerEvent{
        fun onItemClick(position: Int)

        fun onCheckBoxClick(position: Int, isChecked: Boolean)
    }
}