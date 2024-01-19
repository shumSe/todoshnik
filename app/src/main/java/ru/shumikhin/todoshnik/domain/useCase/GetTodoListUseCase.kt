package ru.shumikhin.todoshnik.domain.useCase

import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository

class GetTodoListUseCase(private val todoItemRepository: TodoItemRepository) {

    fun execute(): List<TodoItem> {
        return todoItemRepository.getTodoList()
    }
}