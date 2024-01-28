package ru.shumikhin.todoshnik.presentation.fragments.mainFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.shumikhin.todoshnik.TodoApplication
import ru.shumikhin.todoshnik.domain.useCase.GetTodoListUseCase

class MainFragmentViewModelFactory(application: TodoApplication) : ViewModelProvider.Factory {

    private val todoItemRepository by lazy {
        application.repository
    }

    private val getTodoListUseCase by lazy { GetTodoListUseCase(todoItemRepository = todoItemRepository) }
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainFragmentViewModel(getTodoListUseCase = getTodoListUseCase) as T
    }

}