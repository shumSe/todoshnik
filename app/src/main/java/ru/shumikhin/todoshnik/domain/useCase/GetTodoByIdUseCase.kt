package ru.shumikhin.todoshnik.domain.useCase

import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository

class GetTodoByIdUseCase(private val todoItemRepository: TodoItemRepository) {

    fun execute(id: String):TodoItem{
        return todoItemRepository.getTodo(id)
    }
}