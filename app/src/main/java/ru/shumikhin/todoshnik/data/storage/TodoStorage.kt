package ru.shumikhin.todoshnik.data.storage

import kotlinx.coroutines.flow.Flow
import ru.shumikhin.todoshnik.data.model.TodoItemStorage

interface TodoStorage {

    fun getTodoList(): Flow<List<TodoItemStorage>>

    suspend fun addTodo(todo: TodoItemStorage)

    suspend fun getTodo(id: String): TodoItemStorage

    suspend fun updateTodo(todo: TodoItemStorage)

    suspend fun deleteTodo(todo: TodoItemStorage)

}