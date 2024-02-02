package ru.shumikhin.todoshnik.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.shumikhin.todoshnik.R
import ru.shumikhin.todoshnik.TodoApplication
import ru.shumikhin.todoshnik.databinding.ActivityMainBinding
import ru.shumikhin.todoshnik.di.components.ActivityComponent

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var activityComponent: ActivityComponent
        private set
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = (application as TodoApplication).appComponent.activityComponentFactory().create()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}