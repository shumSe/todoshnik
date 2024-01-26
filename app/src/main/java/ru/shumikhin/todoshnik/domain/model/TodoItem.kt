package ru.shumikhin.todoshnik.domain.model

import android.os.Parcelable
import ru.shumikhin.todoshnik.utils.Importance
import java.util.Date
import java.util.UUID

data class TodoItem(
    val id: UUID,
    val text: String,
    var importance: Importance,
    var deadline: Date?,
    var isCompleted: Boolean,
    val createdAt: Date,
    var changedAt: Date,
)