package ru.shumikhin.todoshnik.di.modules

import dagger.Module
import dagger.Provides
import ru.shumikhin.todoshnik.domain.repository.TodoItemRepository
import ru.shumikhin.todoshnik.domain.useCase.AddTodoUseCase
import ru.shumikhin.todoshnik.domain.useCase.DeleteTodoUseCase
import ru.shumikhin.todoshnik.domain.useCase.GetCompletedTodoCountUseCase
import ru.shumikhin.todoshnik.domain.useCase.GetTodoByIdUseCase
import ru.shumikhin.todoshnik.domain.useCase.GetTodoListUseCase
import ru.shumikhin.todoshnik.domain.useCase.UpdateTodoUseCase

@Module
interface DomainModule {
    companion object {
        @Provides
        fun provideGetTodoListUseCase(todoItemRepository: TodoItemRepository): GetTodoListUseCase {
            return GetTodoListUseCase(todoItemRepository = todoItemRepository)
        }


        @Provides
        fun provideUpdateTodoUseCase(todoItemRepository: TodoItemRepository): UpdateTodoUseCase {
            return UpdateTodoUseCase(todoItemRepository = todoItemRepository)
        }


        @Provides
        fun provideGetCompletedTodoCountUseCase(): GetCompletedTodoCountUseCase {
            return GetCompletedTodoCountUseCase()
        }

        @Provides
        fun provideAddTodoUseCase(todoItemRepository: TodoItemRepository): AddTodoUseCase {
            return AddTodoUseCase(todoItemRepository = todoItemRepository)
        }

        @Provides
        fun provideGetTodoByIdUseCase(todoItemRepository: TodoItemRepository): GetTodoByIdUseCase {
            return GetTodoByIdUseCase(todoItemRepository = todoItemRepository)
        }

        @Provides
        fun provideDeleteTodoUseCase(todoItemRepository: TodoItemRepository): DeleteTodoUseCase {
            return DeleteTodoUseCase(todoItemRepository = todoItemRepository)
        }
    }
}