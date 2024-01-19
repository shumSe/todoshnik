package ru.shumikhin.todoshnik.utils

import ru.shumikhin.todoshnik.data.model.TodoItemStorage
import ru.shumikhin.todoshnik.domain.model.TodoItem

class TodoFormatter {

    private val dateFormatter = DateFormatter()

    fun modelToStorage(item: TodoItem): TodoItemStorage {
        val deadline =
            if (item.deadline == null) null else dateFormatter.dateToString(item.deadline!!)

        val result = TodoItemStorage(
            id = item.id,
            text = item.text,
            importance = item.importance,
            deadline = deadline,
            isCompleted = item.isCompleted,
            createdAt = dateFormatter.dateToString(item.createdAt),
            changedAt = dateFormatter.dateToString(item.changedAt)
        )

        return result
    }

    fun storageToModel(item: TodoItemStorage): TodoItem {
        val deadline =
            if (item.deadline == null) null else dateFormatter.stringToDate(item.deadline!!)
        val result = TodoItem(
            id = item.id,
            text = item.text,
            importance = item.importance,
            deadline = deadline,
            isCompleted = item.isCompleted,
            createdAt = dateFormatter.stringToDate(item.createdAt),
            changedAt = dateFormatter.stringToDate(item.changedAt)
        )
        return result
    }
}