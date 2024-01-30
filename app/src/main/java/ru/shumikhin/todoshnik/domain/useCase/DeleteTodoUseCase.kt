package ru.shumikhin.todoshnik.domain.useCase

import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository

class DeleteTodoUseCase(
    private val todoItemRepository: TodoItemRepository
) {

    suspend fun execute(todo: TodoItem){
        todoItemRepository.deleteTodo(todo)
    }

}