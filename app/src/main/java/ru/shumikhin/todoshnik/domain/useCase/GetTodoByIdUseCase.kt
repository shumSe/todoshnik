package ru.shumikhin.todoshnik.domain.useCase

import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository
import javax.inject.Inject

class GetTodoByIdUseCase @Inject constructor(private val todoItemRepository: TodoItemRepository) {

    suspend fun execute(id: String):TodoItem{
        return todoItemRepository.getTodo(id)
    }
}