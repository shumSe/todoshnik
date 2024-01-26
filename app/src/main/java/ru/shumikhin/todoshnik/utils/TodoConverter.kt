package ru.shumikhin.todoshnik.utils

import ru.shumikhin.todoshnik.data.model.TodoItemStorage
import ru.shumikhin.todoshnik.data.storage.localstorage.room.model.TodoItemDb
import ru.shumikhin.todoshnik.domain.model.TodoItem
import java.util.UUID

class TodoConverter {

    private val dateConverter = DateConverter()

    fun domainToRepository(item: TodoItem): TodoItemStorage {
        val deadline =
            if (item.deadline == null) null else dateConverter.dateToTimestamp(item.deadline!!)

        val result = TodoItemStorage(
            id = item.id.toString(),
            text = item.text,
            importance = item.importance.toString().lowercase(),
            deadline = deadline,
            isCompleted = item.isCompleted,
            createdAt = dateConverter.dateToTimestamp(item.createdAt),
            changedAt = dateConverter.dateToTimestamp(item.changedAt)
        )

        return result
    }

    fun repositoryToDomain(item: TodoItemStorage): TodoItem {
        val deadline =
            if (item.deadline == null) null else dateConverter.timestampToDate(item.deadline!!)
        val result = TodoItem(
            id = UUID.fromString(item.id),
            text = item.text,
            importance = Importance.valueOf(item.importance.uppercase()),
            deadline = deadline,
            isCompleted = item.isCompleted,
            createdAt = dateConverter.timestampToDate(item.createdAt),
            changedAt = dateConverter.timestampToDate(item.changedAt)
        )
        return result
    }

    fun repositoryToRoom(item: TodoItemStorage): TodoItemDb{
        return TodoItemDb(
            id = item.id,
            text = item.text,
            importance = item.importance,
            deadline = item.deadline,
            isCompleted = item.isCompleted,
            createdAt = item.createdAt,
            changedAt = item.changedAt
        )
    }

    fun roomToRepository(item: TodoItemDb): TodoItemStorage{
        return TodoItemStorage(
            id = item.id,
            text = item.text,
            importance = item.importance,
            deadline = item.deadline,
            isCompleted = item.isCompleted,
            createdAt = item.createdAt,
            changedAt = item.changedAt
        )
    }
}