package ru.shumikhin.todoshnik.di.modules

import dagger.Binds
import dagger.Module
import ru.shumikhin.todoshnik.data.repository.TodoItemRepositoryImpl
import ru.shumikhin.todoshnik.di.components.AppScope
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository

@Module(includes = [LocalStorageModule::class])
interface DataModule {
    @Binds
    @AppScope
    fun bindRepository(imp: TodoItemRepositoryImpl): TodoItemRepository
}