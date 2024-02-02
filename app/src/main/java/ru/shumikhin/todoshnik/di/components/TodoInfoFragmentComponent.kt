package ru.shumikhin.todoshnik.di.components

import dagger.Subcomponent
import ru.shumikhin.todoshnik.presentation.fragments.todoInfoFragment.TodoInfoFragment
import javax.inject.Scope


@Scope
annotation class TodoInfoScope
@Subcomponent
@TodoInfoScope
interface TodoInfoFragmentComponent {
    fun inject(todoInfoFragment: TodoInfoFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): TodoInfoFragmentComponent
    }
}