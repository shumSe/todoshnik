package ru.shumikhin.todoshnik.data.model


data class TodoItemStorage(
    val id: String,
    val text: String,
    var importance: String,
    var deadline: Long?,
    var isCompleted: Boolean,
    val createdAt: Long,
    var changedAt: Long,
    )