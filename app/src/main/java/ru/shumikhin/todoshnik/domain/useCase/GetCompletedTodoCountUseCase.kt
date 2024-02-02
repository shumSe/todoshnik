package ru.shumikhin.todoshnik.domain.useCase

import ru.shumikhin.todoshnik.domain.model.TodoItem
import javax.inject.Inject

class GetCompletedTodoCountUseCase @Inject constructor() {
    private var res = 0

    fun execute(todoList: List<TodoItem>): Int{
        res = todoList.count {
            it.isCompleted
        }
        return res
    }

}