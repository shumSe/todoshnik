package ru.shumikhin.todoshnik.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.shumikhin.todoshnik.data.storage.TodoStorage
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository
import ru.shumikhin.todoshnik.utils.TodoConverter
import javax.inject.Inject

class TodoItemRepositoryImpl @Inject constructor(
    private val todoLocalStorage: TodoStorage,
) : TodoItemRepository {

    private val todoConverter = TodoConverter

    override fun getTodoList(): Flow<List<TodoItem>> {
        return todoLocalStorage.getTodoList()
            .map { it.map { todoConverter.repositoryToDomain(it) } }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun addTodo(todo: TodoItem) {
        val item = todoConverter.domainToRepository(todo)
        withContext(Dispatchers.IO) {
            todoLocalStorage.addTodo(item)
        }
    }

    override suspend fun getTodo(id: String): TodoItem = withContext(Dispatchers.IO){
        return@withContext todoConverter.repositoryToDomain(
            todoLocalStorage.getTodo(id)
        )
    }

    override suspend fun updateTodo(todo: TodoItem) = withContext(Dispatchers.IO) {
        val todoRepo = todoConverter.domainToRepository(todo)
        todoLocalStorage.updateTodo(todoRepo)
    }

    override suspend fun deleteTodo(todo: TodoItem) = withContext(Dispatchers.IO) {
        val todoRepo = todoConverter.domainToRepository(todo)
        todoLocalStorage.deleteTodo(todoRepo)
    }
}