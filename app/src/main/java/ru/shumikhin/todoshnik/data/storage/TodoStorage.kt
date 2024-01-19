package ru.shumikhin.todoshnik.data.storage

import ru.shumikhin.todoshnik.data.model.TodoItemStorage

interface TodoStorage {

    fun getTodoList(): List<TodoItemStorage>

    fun addTodo(todo: TodoItemStorage)

    fun getTodo(): TodoItemStorage

}