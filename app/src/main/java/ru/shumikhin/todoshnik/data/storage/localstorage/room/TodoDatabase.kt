package ru.shumikhin.todoshnik.data.storage.localstorage.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.shumikhin.todoshnik.data.storage.localstorage.room.model.TodoItemDb

@Database(entities = [TodoItemDb::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

}