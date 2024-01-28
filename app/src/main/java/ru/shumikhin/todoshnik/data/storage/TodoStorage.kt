package ru.shumikhin.todoshnik.data.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.shumikhin.todoshnik.data.model.TodoItemStorage

interface TodoStorage {

    fun getTodoList(): Flow<List<TodoItemStorage>>

    fun addTodo(todo: TodoItemStorage)

    fun getTodo(id: String): TodoItemStorage

}