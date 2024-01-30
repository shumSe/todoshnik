package ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import ru.shumikhin.todoshnik.R
import ru.shumikhin.todoshnik.TodoApplication
import ru.shumikhin.todoshnik.databinding.FragmentTodoInfoBinding
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.utils.Importance


class TodoInfoFragment : Fragment() {

    private var _binding: FragmentTodoInfoBinding? = null
    private val binding get() = _binding!!

    private val args: TodoInfoFragmentArgs by navArgs()

    private val todoInfoViewModel: TodoInfoViewModel by viewModels {
        TodoInfoViewModelFactory(
            requireActivity().application as TodoApplication,
            args.todoId
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoInfoBinding.inflate(inflater, container, false)

        val aa = ArrayAdapter.createFromResource(requireContext(), R.array.importance_entries, R.layout.spinner_importance_item)
        aa.setDropDownViewResource(R.layout.spinner_importance_dropdown_item)
        val spinner = binding.spinnerImportance
        spinner.adapter = aa
        spinner.setSelection(Importance.BASIC.ordinal)
        setListeners()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    todoInfoViewModel.todo.collect { todoItem ->
                        setViewsData(todoItem)
                    }
                }

                launch {
                    todoInfoViewModel.isAddMode.collect {deleteDisabled ->
                        binding.btnDeleteTodo.isEnabled = deleteDisabled
                        binding.btnDeleteTodo.isClickable = !deleteDisabled
                        if(deleteDisabled){
                            binding.btnDeleteTodo.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.label_disable))
                            binding.btnDeleteTodo.setTextColor(ContextCompat.getColor(requireContext(), R.color.label_disable))
                            binding.btnDeleteTodo.setIconTintResource(R.color.label_disable)
                        }else{
                            binding.btnDeleteTodo.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
                            binding.btnDeleteTodo.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                            binding.btnDeleteTodo.setIconTintResource(R.color.red)
                        }
                    }
                }

                launch {
                    todoInfoViewModel.isAddBtnEnabled.collect{isBtnEnabled ->
                        binding.btnSaveTodo.isEnabled = isBtnEnabled
                        binding.btnSaveTodo.isClickable = isBtnEnabled
                        if(isBtnEnabled) binding.btnSaveTodo.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                        else binding.btnSaveTodo.setTextColor(ContextCompat.getColor(requireContext(), R.color.label_disable))
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                todoInfoViewModel.navEvents.collect{event ->
                    when(event){
                        is TodoInfoViewModel.NavEvent.NavigateToMainScreen -> {
                            findNavController().popBackStack()
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

    private fun setViewsData(todo: TodoItem){
        if(todo.text.isEmpty()){
            return
        }
        binding.etTodoText.setText(todo.text)
        binding.spinnerImportance.setSelection(todo.importance.ordinal)
    }

    private fun setListeners(){
        binding.etTodoText.addTextChangedListener {
            todoInfoViewModel.updateTodoText(it.toString())
        }

        binding.btnSaveTodo.setOnClickListener {
            todoInfoViewModel.onAddBtnClick()
        }

        binding.btnDeleteTodo.setOnClickListener {
            todoInfoViewModel.onDeleteBtnClick()
        }

        binding.spinnerImportance.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                todoInfoViewModel.updateImportance(Importance.entries[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }
}