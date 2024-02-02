package ru.shumikhin.todoshnik.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.shumikhin.todoshnik.data.storage.TodoStorage
import ru.shumikhin.todoshnik.data.storage.localstorage.TodoLocalStorageImpl
import ru.shumikhin.todoshnik.data.storage.localstorage.room.TodoDao
import ru.shumikhin.todoshnik.data.storage.localstorage.room.TodoDatabase
import ru.shumikhin.todoshnik.di.components.AppContext

data class DatabaseConfig(val dbName: String)

@Module
interface LocalStorageModule {

    @Binds
    fun provideLocalStorage(impl: TodoLocalStorageImpl): TodoStorage
    companion object{
        @Provides
        fun provideDao(database: TodoDatabase): TodoDao {
            return database.todoDao()
        }

        @Provides
        fun provideDatabase(@AppContext context: Context, config: DatabaseConfig): TodoDatabase {
            return Room.databaseBuilder(
                context,
                TodoDatabase::class.java,
                config.dbName
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        fun provideDatabaseConfig(): DatabaseConfig {
            return DatabaseConfig(dbName = "todo_database.db")
        }
    }

}