package ru.shumikhin.todoshnik.presentation.fragments.mainFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.shumikhin.todoshnik.R
import ru.shumikhin.todoshnik.TodoApplication
import ru.shumikhin.todoshnik.data.storage.localstorage.TodoLocalStorageImpl
import ru.shumikhin.todoshnik.data.repository.TodoItemRepositoryImpl
import ru.shumikhin.todoshnik.databinding.FragmentMainBinding
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.useCase.GetTodoListUseCase
import ru.shumikhin.todoshnik.presentation.fragments.mainFragment.adapter.TodoAdapter
import ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment.TodoInfoFragment


class MainFragment : Fragment(), TodoAdapter.TodoRecyclerEvent {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    private val mainViewModel: MainFragmentViewModel by viewModels { MainFragmentViewModelFactory(
        requireActivity().application as TodoApplication
    ) }

    private lateinit var dataList: List<TodoItem>

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.tasks.collect{
                    todoAdapter.todoList = it
                }
            }
        }


//        todoAdapter.todoList = dataList
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
        Toast.makeText(requireContext(), todo.importance.toString(), Toast.LENGTH_SHORT).show()
        val bundle = bundleOf("todo_id" to todo.id.toString())
        findNavController().navigate(R.id.action_mainFragment_to_todoInfoFragment, bundle)
    }
}