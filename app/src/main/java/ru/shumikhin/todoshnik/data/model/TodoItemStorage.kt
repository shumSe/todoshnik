package ru.shumikhin.todoshnik.data.model

import ru.shumikhin.todoshnik.utils.Importance

data class TodoItemStorage(
    val id: String,
    val text: String,
    var importance: Importance,
    var deadline: String?,
    var isCompleted: Boolean,
    val createdAt: String,
    var changedAt: String,
    )
