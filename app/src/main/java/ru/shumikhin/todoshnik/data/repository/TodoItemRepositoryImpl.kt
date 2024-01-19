package ru.shumikhin.todoshnik.data.repository

import ru.shumikhin.todoshnik.data.storage.TodoStorage
import ru.shumikhin.todoshnik.domain.model.TodoItem
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository
import ru.shumikhin.todoshnik.utils.TodoFormatter

class TodoItemRepositoryImpl(private val todoLocalStorage: TodoStorage) : TodoItemRepository {

    private val todoFormatter = TodoFormatter()

    override fun getTodoList(): List<TodoItem> {
        val todoList = todoLocalStorage.getTodoList().map { todoFormatter.storageToModel(it) }
        return todoList
    }

    override fun addTodo(todo: TodoItem) {
        TODO("Not yet implemented")
    }

    override fun getTodo(id: String): TodoItem {
        TODO("Not yet implemented")
    }
}