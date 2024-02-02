package ru.shumikhin.todoshnik.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.shumikhin.todoshnik.TodoApplication
import ru.shumikhin.todoshnik.di.modules.DataModule
import ru.shumikhin.todoshnik.di.modules.DomainModule
import ru.shumikhin.todoshnik.di.modules.ViewModelModule
import javax.inject.Qualifier
import javax.inject.Scope

@Scope
annotation class AppScope

@Qualifier
annotation class AppContext

@AppScope
@Component(modules = [DataModule::class, DomainModule::class, ViewModelModule::class])
interface AppComponent {

    fun activityComponentFactory(): ActivityComponent.Factory

    fun inject(application: TodoApplication)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance
            @AppContext
            appContext: Context
        ): AppComponent
    }
}