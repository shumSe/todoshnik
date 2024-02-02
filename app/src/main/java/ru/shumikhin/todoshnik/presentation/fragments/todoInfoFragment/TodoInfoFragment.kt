package ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import ru.shumikhin.todoshnik.R
import ru.shumikhin.todoshnik.TodoApplication
import ru.shumikhin.todoshnik.databinding.FragmentTodoInfoBinding
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.presentation.MainActivity
import ru.shumikhin.todoshnik.presentation.fragments.datePickerFragment.DatePickerFragment
import ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment.adapter.ImportanceSpinnerAdapter
import ru.shumikhin.todoshnik.utils.DateConverter
import ru.shumikhin.todoshnik.utils.Importance
import javax.inject.Inject


class TodoInfoFragment : Fragment() {

    private var _binding: FragmentTodoInfoBinding? = null
    private val binding get() = _binding!!

    private val args: TodoInfoFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val todoInfoViewModel: TodoInfoViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoInfoBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).activityComponent.todoInfoFragmentComponentFactory()
            .create().inject(this)


        val spinnerAdapter = ImportanceSpinnerAdapter(requireContext(), Importance.entries.toList())

        val spinner = binding.spinnerImportance
        spinner.adapter = spinnerAdapter
        spinner.setSelection(Importance.BASIC.ordinal)

        loadArgs()
        setListeners()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    todoInfoViewModel.todo.collect { todoItem ->
                        setViewsData(todoItem)
                        todoInfoViewModel.validate()
                    }
                }

                launch {
                    todoInfoViewModel.isAddMode.collect { deleteDisabled ->
                        binding.btnDeleteTodo.isEnabled = !deleteDisabled
                        binding.btnDeleteTodo.isClickable = !deleteDisabled
                        if (deleteDisabled) {
                            binding.btnDeleteTodo.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.label_disable
                                )
                            )
                            binding.btnDeleteTodo.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.label_disable
                                )
                            )
                            binding.btnDeleteTodo.setIconTintResource(R.color.label_disable)
                        } else {
                            binding.btnDeleteTodo.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red
                                )
                            )
                            binding.btnDeleteTodo.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red
                                )
                            )
                            binding.btnDeleteTodo.setIconTintResource(R.color.red)
                        }
                    }
                }

                launch {
                    todoInfoViewModel.isAddBtnEnabled.collect { isBtnEnabled ->
                        binding.btnSaveTodo.isEnabled = isBtnEnabled
                        binding.btnSaveTodo.isClickable = isBtnEnabled
                        if (isBtnEnabled) binding.btnSaveTodo.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.blue
                            )
                        )
                        else binding.btnSaveTodo.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.label_disable
                            )
                        )
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                todoInfoViewModel.navEvents.collect { event ->
                    when (event) {
                        is TodoInfoViewModel.NavEvent.NavigateToMainScreen -> {
                            findNavController().navigate(R.id.action_todoInfoFragment_to_mainFragment)
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

    private fun setViewsData(todo: TodoItem) {
        binding.etTodoText.setText(todo.text)
        binding.spinnerImportance.setSelection(todo.importance.ordinal)
        todo.deadline?.let {
            binding.tvInfoDeadline.text = DateConverter.timestampToString(it)
            binding.tvInfoDeadline.visibility = View.VISIBLE
            binding.switchDeadline.isChecked = true
        }

    }

    private fun loadArgs() {
        args.todoId?.let {
            todoInfoViewModel.setTodoId(it)
        }
    }

    private fun setListeners() {

        binding.etTodoText.addTextChangedListener {
            todoInfoViewModel.updateTodoText(it.toString().trim())
        }

        binding.btnSaveTodo.setOnClickListener {
            todoInfoViewModel.onSaveBtnClick()
        }

        binding.btnDeleteTodo.setOnClickListener {
            todoInfoViewModel.onDeleteBtnClick()
        }

        binding.btnCancel.setOnClickListener {
            todoInfoViewModel.onCancelBtnClick()
        }

        binding.tvDoUntil.setOnClickListener {
            showDatePicker()
            binding.etTodoText.isCursorVisible = false
        }

        binding.tvInfoDeadline.setOnClickListener {
            showDatePicker()
        }

        binding.root.setOnClickListener {
            binding.etTodoText.isCursorVisible = false
        }

        binding.switchDeadline.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (todoInfoViewModel.todo.value.deadline != null) {
                    return@setOnCheckedChangeListener
                }
                showDatePicker()
            } else {
                binding.tvInfoDeadline.visibility = View.GONE
                todoInfoViewModel.updateDeadline(null)
            }
        }

        binding.spinnerImportance.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                todoInfoViewModel.updateImportance(Importance.entries[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun showDatePicker() {
        val todoDeadline =
            if (todoInfoViewModel.todo.value.deadline != null) todoInfoViewModel.todo.value.deadline else DATE_PICKER_EMPTY
        val datePicker = DatePickerFragment(todoDeadline!!)
        val fragManager = requireActivity().supportFragmentManager
        fragManager.setFragmentResultListener(
            "REQUEST_KEY",
            viewLifecycleOwner
        ) { resultKey, bundle ->
            if (resultKey == "REQUEST_KEY") {
                val date = bundle.getLong("SELECTED_DATE")
                if (date == DATE_PICKER_EMPTY) {
                    binding.switchDeadline.isChecked = false
                    todoInfoViewModel.updateDeadline(null)
                    return@setFragmentResultListener
                }
                val stringDeadline = DateConverter.timestampToString(date)
                binding.tvInfoDeadline.text = stringDeadline
                binding.tvInfoDeadline.visibility = View.VISIBLE
                todoInfoViewModel.updateDeadline(date)
            }

        }

        datePicker.show(fragManager, "datePicker")
    }

    companion object {
        const val DATE_PICKER_EMPTY: Long = 0
    }
}
