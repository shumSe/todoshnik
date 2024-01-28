package ru.shumikhin.todoshnik.data.storage.localstorage.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.shumikhin.todoshnik.data.storage.localstorage.room.model.TodoItemDb

@Dao
interface TodoDao {
    @Query("SELECT * FROM todoitemdb")
    fun getAll(): Flow<List<TodoItemDb>>

    @Query("SELECT * FROM todoitemdb WHERE id=:id")
    suspend fun getById(id : String): TodoItemDb

    @Insert
    suspend fun addTodo(todo: TodoItemDb)

    @Update
    suspend fun updateTodo(todo: TodoItemDb)

    @Delete
    suspend  fun deleteTodo(todo: TodoItemDb)
}