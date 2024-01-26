package ru.shumikhin.todoshnik.data.storage.localstorage.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.shumikhin.todoshnik.data.storage.localstorage.room.model.TodoItemDb

@Dao
interface TodoDao {
    @Query("SELECT * FROM todoitemdb")
    fun getAll(): List<TodoItemDb>

    @Query("SELECT * FROM todoitemdb WHERE id=:id")
    fun getById(id : String): TodoItemDb

    @Insert
    fun addTodo(todo: TodoItemDb)

    @Update
    fun updateTodo(todo: TodoItemDb)

    @Delete
    fun deleteTodo(todo: TodoItemDb)
}