package ru.shumikhin.todoshnik.domain.useCase

import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository
import javax.inject.Inject

class AddTodoUseCase @Inject constructor (private val todoItemRepository: TodoItemRepository) {

    suspend fun execute(todo: TodoItem) {
        todoItemRepository.addTodo(todo)
    }
}