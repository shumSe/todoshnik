package ru.shumikhin.todoshnik

import android.app.Application
import ru.shumikhin.todoshnik.data.repository.TodoItemRepositoryImpl
import ru.shumikhin.todoshnik.data.storage.localstorage.TodoLocalStorageImpl
import ru.shumikhin.todoshnik.data.storage.localstorage.room.TodoDatabase
import ru.shumikhin.todoshnik.utils.TodoConverter

class TodoApplication: Application() {

    val database by lazy { TodoDatabase.getDatabase(this) }

    val converter = TodoConverter()
    val repository by lazy { TodoItemRepositoryImpl( TodoLocalStorageImpl(database.todoDao(), converter)) }
}