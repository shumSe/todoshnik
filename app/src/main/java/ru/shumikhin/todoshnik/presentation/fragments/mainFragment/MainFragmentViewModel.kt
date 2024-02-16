package ru.shumikhin.todoshnik.presentation.fragments.mainFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.useCase.GetCompletedTodoCountUseCase
import ru.shumikhin.todoshnik.domain.useCase.GetTodoListUseCase
import ru.shumikhin.todoshnik.domain.useCase.UpdateTodoUseCase
import java.util.Date
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val getTodoListUseCase: GetTodoListUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val getCompletedTodoCount: GetCompletedTodoCountUseCase,
) : ViewModel() {

    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val tasks: StateFlow<List<TodoItem>> = _todoList.asStateFlow()

    private val _tasksEvent = MutableSharedFlow<TasksEvent>()
    val tasksEvent = _tasksEvent.asSharedFlow()

    private val _completedCount = MutableStateFlow<Int>(0)
    val completedCount = _completedCount.asStateFlow()

    private val _isHideCompleted = MutableStateFlow<Boolean>(false)
    val isHideCompleted = _isHideCompleted.asStateFlow()

    private var uncompletedList = emptyList<TodoItem>()
    private var cachedList = emptyList<TodoItem>()

    init {
        viewModelScope.launch {
            getTodoListUseCase.execute().collect { itemsList ->
                _completedCount.emit(
                    getCompletedTodoCount.execute(itemsList)
                )
                uncompletedList = itemsList.filter { !it.isCompleted }
                cachedList = itemsList
                if (_isHideCompleted.value) {
                    _todoList.emit(uncompletedList)
                } else {
                    _todoList.emit(cachedList)
                }
            }
        }
    }

    fun onTaskClicked(position: Int) {
        viewModelScope.launch {
            val todoId = _todoList.value[position].id.toString()
            _tasksEvent.emit(
                TasksEvent.NavigateToEditTaskScreen(todoId)
            )
        }
    }

    fun onTaskCompletedChanged(position: Int, isChecked: Boolean) {
        viewModelScope.launch {
            val todo =
                _todoList.value[position].copy(isCompleted = isChecked, changedAt = Date().time)
            updateTodoUseCase.execute(todo)
        }
    }

    fun onAddBtnClicked() = viewModelScope.launch {
        _tasksEvent.emit(TasksEvent.NavigateToAddTaskScreen)
    }

    fun onHideCompletedClicked() {
        viewModelScope.launch {
            _isHideCompleted.emit(!_isHideCompleted.value)
            if (_isHideCompleted.value) {
                _todoList.emit(uncompletedList)
            } else {
                _todoList.emit(cachedList)
            }
        }
    }

    sealed class TasksEvent {
        data object NavigateToAddTaskScreen : TasksEvent()
        data class NavigateToEditTaskScreen(val id: String) : TasksEvent()

    }

}