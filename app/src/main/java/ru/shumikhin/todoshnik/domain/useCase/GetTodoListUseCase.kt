package ru.shumikhin.todoshnik.domain.useCase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository

class GetTodoListUseCase(private val todoItemRepository: TodoItemRepository) {

    fun execute(): Flow<List<TodoItem>> {
        return todoItemRepository.getTodoList()
    }
}