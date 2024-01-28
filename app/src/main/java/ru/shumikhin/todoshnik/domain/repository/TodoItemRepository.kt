package ru.shumikhin.todoshnik.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.shumikhin.todoshnik.domain.model.TodoItem

interface TodoItemRepository {

    fun getTodoList(): Flow<List<TodoItem>>

    suspend fun addTodo(todo: TodoItem)

    suspend fun getTodo(id: String): TodoItem

    suspend fun updateTodo(todo: TodoItem)

}