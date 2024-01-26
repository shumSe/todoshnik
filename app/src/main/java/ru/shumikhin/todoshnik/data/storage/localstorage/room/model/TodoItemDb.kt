package ru.shumikhin.todoshnik.data.storage.localstorage.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TodoItemDb(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "importance")
    var importance: String,
    @ColumnInfo(name = "deadline")
    var deadline: Long?,
    @ColumnInfo(name = "is_completed")
    var isCompleted: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "changed_at")
    var changedAt: Long,
)