package ru.shumikhin.todoshnik.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import ru.shumikhin.todoshnik.data.storage.TodoStorage
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository
import ru.shumikhin.todoshnik.utils.TodoConverter

class TodoItemRepositoryImpl(private val todoLocalStorage: TodoStorage) : TodoItemRepository {

    private val todoConverter = TodoConverter()

    override fun getTodoList(): Flow<List<TodoItem>> {
        val todoList = todoLocalStorage.getTodoList().map { it.map{todoConverter.repositoryToDomain(it)} }
        return todoList
    }

    override fun addTodo(todo: TodoItem) {
        val item = todoConverter.domainToRepository(todo)
        todoLocalStorage.addTodo(item)
    }

    override fun getTodo(id: String): TodoItem {
        val res = todoConverter.repositoryToDomain(todoLocalStorage.getTodo(id))
        return res
    }
}