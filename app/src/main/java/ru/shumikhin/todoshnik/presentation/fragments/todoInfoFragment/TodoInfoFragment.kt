package ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ru.shumikhin.todoshnik.R
import ru.shumikhin.todoshnik.TodoApplication
import ru.shumikhin.todoshnik.databinding.FragmentTodoInfoBinding
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.useCase.AddTodoUseCase
import ru.shumikhin.todoshnik.domain.useCase.GetTodoByIdUseCase
import ru.shumikhin.todoshnik.utils.Importance
import java.util.Date
import java.util.UUID


class TodoInfoFragment : Fragment() {

    private var _binding: FragmentTodoInfoBinding? = null
    private val binding get() = _binding!!

    private val todoItemRepository by lazy{
        (requireActivity().application as TodoApplication).repository
    }

    private val addTodoUseCase by lazy {  AddTodoUseCase(todoItemRepository = todoItemRepository) }

    private val getTodoByIdUseCase by lazy { GetTodoByIdUseCase(todoItemRepository = todoItemRepository) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoInfoBinding.inflate(inflater, container, false)

        val aa = ArrayAdapter.createFromResource(requireContext(), R.array.importance_entries, R.layout.spinner_importance_item)
        aa.setDropDownViewResource(R.layout.spinner_importance_dropdown_item)
        val spinner = binding.spinnerImportance
        spinner.adapter = aa

        val todoId = arguments?.getString("todo_id")

        if(todoId == null) {
            spinner.setSelection(Importance.BASIC.ordinal)
        }
        else {
            setViewsData(todoId)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSaveTodo.setOnClickListener {
            val todo = buildTodo()
            addTodoUseCase.execute(todo)
            Toast.makeText(requireContext(), "TODO ADDED", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_todoInfoFragment_to_mainFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun buildTodo(): TodoItem {
        val d = Date()
        val id = UUID.randomUUID()
        val text = binding.etTodoText.text.toString()
        val importance = Importance.entries[binding.spinnerImportance.selectedItemPosition]
        val deadline = null
        val isCompleted = false
        val createdAt = d
        val changedAt = d

        return TodoItem(
            id,
            text,
            importance,
            deadline,
            isCompleted,
            createdAt,
            changedAt
        )
    }

    private fun setViewsData(id: String){
        val todo = getTodoByIdUseCase.execute(id)
        binding.etTodoText.setText(todo.text)
        binding.spinnerImportance.setSelection(todo.importance.ordinal)
    }
}