package ru.shumikhin.todoshnik.di.components

import dagger.Subcomponent
import ru.shumikhin.todoshnik.presentation.fragments.mainFragment.MainFragment
import javax.inject.Scope

@Scope
annotation class MainFragmentScope

@Subcomponent
@MainFragmentScope
interface MainFragmentComponent {
    fun inject(mainFragment: MainFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): MainFragmentComponent
    }
}