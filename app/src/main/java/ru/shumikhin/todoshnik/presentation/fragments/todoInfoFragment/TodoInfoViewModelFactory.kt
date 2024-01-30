package ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.shumikhin.todoshnik.TodoApplication
import ru.shumikhin.todoshnik.domain.useCase.AddTodoUseCase
import ru.shumikhin.todoshnik.domain.useCase.DeleteTodoUseCase
import ru.shumikhin.todoshnik.domain.useCase.GetTodoByIdUseCase
import ru.shumikhin.todoshnik.domain.useCase.UpdateTodoUseCase

class TodoInfoViewModelFactory(
    application: TodoApplication,
    private val todoId: String?,
): ViewModelProvider.Factory {

    private val todoItemRepository by lazy {
        application.repository
    }

    private val addTodoUseCase by lazy {
        AddTodoUseCase(todoItemRepository = todoItemRepository)
    }

    private val getTodoByIdUseCase by lazy {
        GetTodoByIdUseCase(todoItemRepository = todoItemRepository)
    }

    private val updateTdoUseCase by lazy {
        UpdateTodoUseCase(todoItemRepository = todoItemRepository)
    }

    private val deleteTodoUseCase by lazy {
        DeleteTodoUseCase(todoItemRepository = todoItemRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return TodoInfoViewModel(
            addTodoUseCase = addTodoUseCase,
            getTodoByIdUseCase = getTodoByIdUseCase,
            updateTodoUseCase = updateTdoUseCase,
            deleteTodoUseCase = deleteTodoUseCase,
            todoId = todoId
        ) as T
    }
}