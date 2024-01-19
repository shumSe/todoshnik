package ru.shumikhin.todoshnik.domain.model

import ru.shumikhin.todoshnik.utils.Importance
import java.util.Date

data class TodoItem(
    val id: String,
    val text: String,
    var importance: Importance,
    var deadline: Date?,
    var isCompleted: Boolean,
    val createdAt: Date,
    var changedAt: Date,
)