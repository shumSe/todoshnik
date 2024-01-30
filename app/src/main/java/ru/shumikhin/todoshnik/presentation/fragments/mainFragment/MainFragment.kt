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
import ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment.TodoInfoFragmentDirections


class MainFragment : Fragment(), TodoAdapter.TodoRecyclerEvent {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    private val mainViewModel: MainFragmentViewModel by viewModels { MainFragmentViewModelFactory(
        requireActivity().application as TodoApplication
    ) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.fabAddTodo.setOnClickListener{
            mainViewModel.onAddBtnClicked()
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
        val recyclerView = binding.todoRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = todoAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.tasksEvent.collect{ event ->
                    when(event){
                        is MainFragmentViewModel.TasksEvent.NavigateToAddTaskScreen -> {
                            val action = MainFragmentDirections.actionMainFragmentToTodoInfoFragment(todoId = null)
                            findNavController().navigate(action)
                        }
                        is MainFragmentViewModel.TasksEvent.NavigateToEditTaskScreen -> {
                            val todoId = event.id
                            val action = MainFragmentDirections.actionMainFragmentToTodoInfoFragment(todoId)
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        mainViewModel.onTaskClicked(position)
    }

    override fun onCheckBoxClick(position: Int, isChecked: Boolean) {
        mainViewModel.onTaskCompletedChanged(position, isChecked)
    }
}