package ru.shumikhin.todoshnik.domain.repository

import ru.shumikhin.todoshnik.domain.model.TodoItem

interface TodoItemRepository {

    fun getTodoList(): List<TodoItem>

    fun addTodo(todo: TodoItem)

    fun getTodo(id: String): TodoItem

}