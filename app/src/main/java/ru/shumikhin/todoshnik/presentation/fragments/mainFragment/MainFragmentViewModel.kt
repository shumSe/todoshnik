package ru.shumikhin.todoshnik.presentation.fragments.mainFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.useCase.GetTodoListUseCase

class MainFragmentViewModel(
    private val getTodoListUseCase: GetTodoListUseCase,
) : ViewModel() {

    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val tasks: StateFlow<List<TodoItem>> = _todoList.asStateFlow()

    private val _tasksEvent = MutableSharedFlow<TasksEvent>()
    val tasksEvent = _tasksEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            getTodoListUseCase.execute().collect{ itemsList ->
                _todoList.emit(itemsList)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MAIN-MODEL", "onCleared: VIEWMODEL")
    }

    fun onTaskClicked(position: Int){
        viewModelScope.launch {
            val todo = _todoList.value[position]
            Log.d("TASK-CLICK", "onTaskClicked: ${todo.text}")
            _tasksEvent.emit(
                TasksEvent.NavigateToEditTaskScreen(todo.id.toString())
            )
        }
    }

    fun onTaskCompletedChanged(position: Int, isChecked: Boolean){
        viewModelScope.launch {
            val todo = _todoList.value[position].copy(isCompleted = isChecked)
            Log.d("CLICK-CHECK", "onTaskCompletedChanged: ${todo.text} - ${todo.isCompleted}")
        }
    }

    fun onAddBtnClicked() = viewModelScope.launch{
        _tasksEvent.emit(TasksEvent.NavigateToAddTaskScreen)
    }

    sealed class TasksEvent{
        data object NavigateToAddTaskScreen: TasksEvent()
        data class NavigateToEditTaskScreen(val id: String): TasksEvent()

    }

}