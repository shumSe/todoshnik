package ru.shumikhin.todoshnik.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.assisted.AssistedFactory
import dagger.multibindings.IntoMap
import ru.shumikhin.todoshnik.presentation.fragments.factory.AppViewModelFactory
import ru.shumikhin.todoshnik.presentation.fragments.mainFragment.MainFragmentViewModel
import ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment.TodoInfoViewModel

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TodoInfoViewModel::class)
    fun bindTodoInfoViewModel(todoInfoViewModel: TodoInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    fun bindMainViewModel(mainViewModel: MainFragmentViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}