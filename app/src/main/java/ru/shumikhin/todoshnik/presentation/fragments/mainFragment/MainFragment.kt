package ru.shumikhin.todoshnik.presentation.fragments.mainFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
import ru.shumikhin.todoshnik.presentation.MainActivity
import ru.shumikhin.todoshnik.presentation.fragments.mainFragment.adapter.TodoAdapter
import ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment.TodoInfoFragment
import ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment.TodoInfoFragmentDirections
import javax.inject.Inject


class MainFragment : Fragment(), TodoAdapter.TodoRecyclerEvent {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mainViewModel: MainFragmentViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).activityComponent.mainFragmentComponentFactory()
            .create().inject(this)


        binding.fabAddTodo.setOnClickListener {
            mainViewModel.onAddBtnClicked()
        }

        binding.btnHideCompleted.setOnClickListener {
            mainViewModel.onHideCompletedClicked()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.completedCount.collect { completedCount ->
                    binding.tvCompletedCount.text = getString(R.string.completed, completedCount)
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todoAdapter = TodoAdapter(this, requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mainViewModel.tasks.collect { taskList ->
                        todoAdapter.submitList(taskList)
                    }
                }
                launch {
                    mainViewModel.isHideCompleted.collect{flag ->
                        if(flag){
                            binding.btnHideCompleted.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.visibility_on))
                        }else{
                            binding.btnHideCompleted.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.visibility_off))
                        }
                    }
                }
            }
        }
        val recyclerView = binding.todoRecycler
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = todoAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.tasksEvent.collect { event ->
                    when (event) {
                        is MainFragmentViewModel.TasksEvent.NavigateToAddTaskScreen -> {
                            val action =
                                MainFragmentDirections.actionMainFragmentToTodoInfoFragment(todoId = null)
                            findNavController().navigate(action)
                        }

                        is MainFragmentViewModel.TasksEvent.NavigateToEditTaskScreen -> {
                            val todoId = event.id
                            val action =
                                MainFragmentDirections.actionMainFragmentToTodoInfoFragment(todoId)
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