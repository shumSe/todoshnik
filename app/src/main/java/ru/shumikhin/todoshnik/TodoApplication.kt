package ru.shumikhin.todoshnik

import android.app.Application
import ru.shumikhin.todoshnik.data.repository.TodoItemRepositoryImpl
import ru.shumikhin.todoshnik.data.storage.localstorage.TodoLocalStorageImpl
import ru.shumikhin.todoshnik.data.storage.localstorage.room.TodoDatabase
import ru.shumikhin.todoshnik.di.components.AppComponent
import ru.shumikhin.todoshnik.di.components.DaggerAppComponent
import ru.shumikhin.todoshnik.utils.TodoConverter

class TodoApplication: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(appContext = this.applicationContext)
        appComponent.inject(this)
    }
}