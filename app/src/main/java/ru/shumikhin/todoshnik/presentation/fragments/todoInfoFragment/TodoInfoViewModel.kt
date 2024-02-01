package ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.useCase.AddTodoUseCase
import ru.shumikhin.todoshnik.domain.useCase.DeleteTodoUseCase
import ru.shumikhin.todoshnik.domain.useCase.GetTodoByIdUseCase
import ru.shumikhin.todoshnik.domain.useCase.UpdateTodoUseCase
import ru.shumikhin.todoshnik.utils.Importance
import java.util.Date
import kotlin.math.log

class TodoInfoViewModel(
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val todoId: String?
): ViewModel() {

    private val _todo = MutableStateFlow<TodoItem>(TodoItem())
    val todo = _todo.asStateFlow()

    private var oldTodo: TodoItem = _todo.value.copy()
        set(value){
            field = value
            validate()
        }


    private val _isAddTodoMode = MutableStateFlow<Boolean>(todoId == null)
    val isAddMode = _isAddTodoMode.asStateFlow()

    private val _navEvents = MutableSharedFlow<NavEvent>()
    val navEvents = _navEvents.asSharedFlow()

    val isAddBtnEnabled = MutableStateFlow(false)


    init {
        viewModelScope.launch {
            todoId?.let {
                _todo.value = getTodoByIdUseCase.execute(it)
                oldTodo = getTodoByIdUseCase.execute(it)
            }
        }
    }

    fun updateTodoText(text: String){
        _todo.value.text = text
        validate()
    }

    fun updateImportance(i: Importance){
        viewModelScope.launch{
            _todo.emit(
                _todo.value.copy(importance = i)
            )
        }
    }
    fun updateDeadline(d: Long?){
        viewModelScope.launch {
            _todo.emit(_todo.value.copy(deadline = d))
        }

    }

    fun onSaveBtnClick(){
        viewModelScope.launch {
            _isAddTodoMode.collect{flag ->
                if(flag){
                    addTodoUseCase.execute(_todo.value)
                } else {
                    updateTodoUseCase.execute(_todo.value.copy(changedAt = Date().time))
                }
                _navEvents.emit(
                    NavEvent.NavigateToMainScreen
                )
            }
        }
    }

    fun onCancelBtnClick(){
        viewModelScope.launch {
            _navEvents.emit(
                NavEvent.NavigateToMainScreen
            )
        }
    }

    fun onDeleteBtnClick(){
        viewModelScope.launch {
            deleteTodoUseCase.execute(_todo.value)
            _navEvents.emit(
                NavEvent.NavigateToMainScreen
            )
        }
    }

    fun validate(){
        if(_todo.value.text.trim().isEmpty()){
            isAddBtnEnabled.value = false
            return
        }
        isAddBtnEnabled.value = oldTodo != _todo.value
    }

    sealed class NavEvent{
        data object NavigateToMainScreen : NavEvent()
    }
}