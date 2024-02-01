package ru.shumikhin.todoshnik.domain.model

import android.os.Parcelable
import ru.shumikhin.todoshnik.utils.Importance
import java.util.Date
import java.util.UUID

data class TodoItem(
    val id: UUID = UUID.randomUUID(),
    var text: String = "",
    var importance: Importance = Importance.BASIC,
    var deadline: Long? = null,
    var isCompleted: Boolean = false,
    val createdAt: Long = Date().time,
    var changedAt: Long = Date().time,
)