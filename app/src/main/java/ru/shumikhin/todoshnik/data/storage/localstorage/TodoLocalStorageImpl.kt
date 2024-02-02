package ru.shumikhin.todoshnik.data.storage.localstorage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import ru.shumikhin.todoshnik.data.model.TodoItemStorage
import ru.shumikhin.todoshnik.data.storage.TodoStorage
import ru.shumikhin.todoshnik.data.storage.localstorage.room.TodoDao
import ru.shumikhin.todoshnik.utils.TodoConverter
import javax.inject.Inject

class TodoLocalStorageImpl @Inject constructor(
    private val todoDao: TodoDao,
): TodoStorage {
    override fun getTodoList(): Flow<List<TodoItemStorage>> {
        return  todoDao.getAll().map {dbItemsList ->
            dbItemsList.map{dbItem ->
                TodoConverter.roomToRepository(dbItem)
            }
        }
    }

    override suspend fun addTodo(todo: TodoItemStorage) {
        val todoDb = TodoConverter.repositoryToRoom(todo)
        todoDao.addTodo(todoDb)
    }

    override suspend fun getTodo(id: String): TodoItemStorage {
        val res = todoDao.getById(id)
        return TodoConverter.roomToRepository(res)
    }

    override suspend fun updateTodo(todo: TodoItemStorage) {
        val todoDb = TodoConverter.repositoryToRoom(todo)
        todoDao.updateTodo(todoDb)
    }

    override suspend fun deleteTodo(todo: TodoItemStorage) {
        val todoDb = TodoConverter.repositoryToRoom(todo)
        todoDao.deleteTodo(todoDb)
    }
}