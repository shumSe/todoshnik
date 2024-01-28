package ru.shumikhin.todoshnik.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.shumikhin.todoshnik.data.storage.TodoStorage
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository
import ru.shumikhin.todoshnik.utils.TodoConverter

class TodoItemRepositoryImpl(private val todoLocalStorage: TodoStorage) : TodoItemRepository {

    private val todoConverter = TodoConverter()

    override fun getTodoList(): Flow<List<TodoItem>> {
        return todoLocalStorage.getTodoList()
            .map { it.map { todoConverter.repositoryToDomain(it) } }
    }

    override suspend fun addTodo(todo: TodoItem) {
        val item = todoConverter.domainToRepository(todo)
        todoLocalStorage.addTodo(item)
    }

    override suspend fun getTodo(id: String): TodoItem {
        return todoConverter.repositoryToDomain(todoLocalStorage.getTodo(id))
    }

    override suspend fun updateTodo(todo: TodoItem) {
        val todoRepo = todoConverter.domainToRepository(todo)
        todoLocalStorage.updateTodo(todoRepo)
    }
}