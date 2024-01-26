package ru.shumikhin.todoshnik.domain.useCase

import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository

class AddTodoUseCase(private val todoItemRepository: TodoItemRepository) {

    fun execute(todo: TodoItem){
        todoItemRepository.addTodo(todo)
    }
}