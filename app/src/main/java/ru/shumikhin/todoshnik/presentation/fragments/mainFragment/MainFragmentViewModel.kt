package ru.shumikhin.todoshnik.presentation.fragments.mainFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.useCase.GetTodoListUseCase

class MainFragmentViewModel(
    private val getTodoListUseCase: GetTodoListUseCase,
) : ViewModel() {

    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val tasks: StateFlow<List<TodoItem>> = _todoList.asStateFlow()

    init {
        viewModelScope.launch {
            getTodoListUseCase.execute().collect{
                _todoList.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MAIN-MODEL", "onCleared: VIEWMODEL")
    }

}