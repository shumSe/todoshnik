package ru.shumikhin.todoshnik.presentation.fragments.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.shumikhin.todoshnik.R
import ru.shumikhin.todoshnik.data.storage.localstorage.TodoLocalStorageImpl
import ru.shumikhin.todoshnik.data.repository.TodoItemRepositoryImpl
import ru.shumikhin.todoshnik.databinding.FragmentMainBinding
import ru.shumikhin.todoshnik.domain.useCase.GetTodoListUseCase
import ru.shumikhin.todoshnik.presentation.fragments.mainFragment.adapter.TodoAdapter


class MainFragment : Fragment(), TodoAdapter.TodoRecyclerEvent {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val todoLocalStorage = TodoLocalStorageImpl()
    private val todoItemRepository = TodoItemRepositoryImpl(
        todoLocalStorage = todoLocalStorage
    )
    private val getListUseCase = GetTodoListUseCase(todoItemRepository = todoItemRepository)

    private val dataList = getListUseCase.execute()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.fabAddTodo.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_todoInfoFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todoAdapter = TodoAdapter(this)
        todoAdapter.todoList = dataList
        val recyclerView = binding.todoRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = todoAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        val todo = dataList[position]
        Toast.makeText(requireContext(), todo.text, Toast.LENGTH_SHORT).show()
    }
}