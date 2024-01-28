package ru.shumikhin.todoshnik.data.storage.localstorage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import ru.shumikhin.todoshnik.data.model.TodoItemStorage
import ru.shumikhin.todoshnik.data.storage.TodoStorage
import ru.shumikhin.todoshnik.data.storage.localstorage.room.TodoDao
import ru.shumikhin.todoshnik.utils.TodoConverter

class TodoLocalStorageImpl(
    private val todoDao: TodoDao,
    private val todoConverter: TodoConverter
): TodoStorage {

    override fun getTodoList(): Flow<List<TodoItemStorage>> {
        val res = todoDao.getAll().map { it.map{todoConverter.roomToRepository(it)} }
        return res
    }

    override fun addTodo(todo: TodoItemStorage) {
        val todoDb = todoConverter.repositoryToRoom(todo)
        todoDao.addTodo(todoDb)
    }

    override fun getTodo(id: String): TodoItemStorage {
        val res = todoDao.getById(id)
        return todoConverter.roomToRepository(res)
    }
}