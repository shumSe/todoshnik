package ru.shumikhin.todoshnik.utils

import ru.shumikhin.todoshnik.data.model.TodoItemStorage
import ru.shumikhin.todoshnik.data.storage.localstorage.room.model.TodoItemDb
import ru.shumikhin.todoshnik.domain.model.TodoItem
import java.util.UUID

object TodoConverter {

    fun domainToRepository(item: TodoItem): TodoItemStorage {
        val result = TodoItemStorage(
            id = item.id.toString(),
            text = item.text,
            importance = item.importance.toString().lowercase(),
            deadline = item.deadline,
            isCompleted = item.isCompleted,
            createdAt = item.createdAt,
            changedAt = item.changedAt
        )

        return result
    }

    fun repositoryToDomain(item: TodoItemStorage): TodoItem {

        val result = TodoItem(
            id = UUID.fromString(item.id),
            text = item.text,
            importance = Importance.valueOf(item.importance.uppercase()),
            deadline = item.deadline,
            isCompleted = item.isCompleted,
            createdAt = item.createdAt,
            changedAt = item.changedAt
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