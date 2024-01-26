package ru.shumikhin.todoshnik.data.storage.localstorage

import ru.shumikhin.todoshnik.data.model.TodoItemStorage
import ru.shumikhin.todoshnik.data.storage.TodoStorage
import ru.shumikhin.todoshnik.data.storage.localstorage.room.TodoDao
import ru.shumikhin.todoshnik.utils.TodoConverter

class TodoLocalStorageImpl(
    private val todoDao: TodoDao,
    private val todoConverter: TodoConverter
): TodoStorage {

    override fun getTodoList(): List<TodoItemStorage> {
        val res = todoDao.getAll().map { todoConverter.roomToRepository(it) }
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