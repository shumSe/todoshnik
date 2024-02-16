package ru.shumikhin.todoshnik.presentation.fragments.mainFragment.adapter

import android.content.Context
import android.graphics.Paint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.text.font.Typeface
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.shumikhin.todoshnik.R
import ru.shumikhin.todoshnik.databinding.TodoListItemBinding
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.utils.DateConverter
import ru.shumikhin.todoshnik.utils.Importance

class TodoAdapter(
    private val listener: TodoRecyclerEvent,
    private val context: Context
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private val differ: AsyncListDiffer<TodoItem> = AsyncListDiffer(this, DiffCallback())

    fun submitList(list: List<TodoItem>) = differ.submitList(list)

    fun currentList(): List<TodoItem> = differ.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TodoListItemBinding.inflate(inflater, parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int = currentList().size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = currentList()[position]
        with(holder.binding) {
            tvTodoTitle.text = todo.text

            if (todo.deadline == null) {
                tvTodoDeadline.visibility = View.GONE
            } else {
                tvTodoDeadline.visibility = View.VISIBLE
                tvTodoDeadline.text = DateConverter.timestampToString(todo.deadline!!)
            }

            checkboxIsCompleted.isChecked = todo.isCompleted

            if (todo.isCompleted) {
                tvTodoTitle.setTextColor(ContextCompat.getColor(context, R.color.label_tertiary))
                tvTodoTitle.paintFlags = (Paint.STRIKE_THRU_TEXT_FLAG)
                checkboxIsCompleted.buttonDrawable =
                    ContextCompat.getDrawable(context, R.drawable.checkbox_checked)
            } else {
                if (todo.importance == Importance.IMPORTANT) {
                    checkboxIsCompleted.buttonDrawable =
                        ContextCompat.getDrawable(context, R.drawable.checkbox_unchecked_high)
                    val spannable = SpannableString("!!${tvTodoTitle.text}")
                    spannable.setSpan(
                        ForegroundColorSpan(ContextCompat.getColor(context, R.color.red)),
                        0, 2,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    tvTodoTitle.text = spannable
                } else {
                    checkboxIsCompleted.buttonDrawable =
                        ContextCompat.getDrawable(context, R.drawable.checkbox_unchecked_normal)
                }
                tvTodoTitle.paintFlags = tvTodoDeadline.paintFlags
                tvTodoTitle.setTextColor(ContextCompat.getColor(context, R.color.label_primary))
            }
        }
    }

    inner class TodoViewHolder(val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.btnTodoInfo.setOnClickListener(this)
            binding.root.setOnClickListener(this)

            binding.checkboxIsCompleted.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onCheckBoxClick(position, binding.checkboxIsCompleted.isChecked)
                }
            }
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface TodoRecyclerEvent {
        fun onItemClick(position: Int)

        fun onCheckBoxClick(position: Int, isChecked: Boolean)
    }


    private class DiffCallback : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean =
            oldItem == newItem

    }
}