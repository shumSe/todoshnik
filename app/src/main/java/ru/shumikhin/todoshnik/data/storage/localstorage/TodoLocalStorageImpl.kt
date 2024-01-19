package ru.shumikhin.todoshnik.data.storage.localstorage

import ru.shumikhin.todoshnik.data.model.TodoItemStorage
import ru.shumikhin.todoshnik.data.storage.TodoStorage
import ru.shumikhin.todoshnik.utils.DateFormatter
import ru.shumikhin.todoshnik.utils.Importance
import java.util.Calendar
import kotlin.random.Random

class TodoLocalStorageImpl : TodoStorage {

    override fun getTodoList(): List<TodoItemStorage> {

        val todoList = mutableListOf<TodoItemStorage>()

        for (i in 1..10) {
            val r = Random.nextInt(10, 100)
            val text = lorem.substring(r)
            val importance = if (r > 50) Importance.HIGH else Importance.STANDART
            val deadline = if(r < 25) DateFormatter().dateToString(Calendar.getInstance().time) else null
            val isCompleted = r < 30
            val createdAt = DateFormatter().dateToString(Calendar.getInstance().time)
            val changedAt = createdAt
            val item = TodoItemStorage(
                id = "$i",
                text = text,
                importance = importance,
                deadline = deadline,
                isCompleted = isCompleted,
                createdAt = createdAt,
                changedAt = changedAt
            )
            todoList.add(item)
        }
        return todoList
    }

    override fun addTodo(todo: TodoItemStorage) {
        TODO("Not yet implemented")
    }

    override fun getTodo(): TodoItemStorage {
        TODO("Not yet implemented")
    }


    private val lorem =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt magna aliqua. Ac turpis egestas integer eget aliquet nibh praesent. Penatibus et magnis dis parturient. Leo vel orci porta non. Ullamcorper morbi tincidunt ornare massa eget egestas. Et malesuada fames ac turpis egestas. Convallis a cras semper auctor. Platea dictumst vestibulum rhoncus est pellentesque. Tempor orci dapibus ultrices in iaculis nunc sed augue. Orci phasellus egestas tellus rutrum. Ultrices mi tempus imperdiet nulla malesuada pellentesque elit. Nunc consequat interdum varius sit amet. Iaculis urna id volutpat lacus laoreet non curabitur gravida arcu. Auctor neque vitae tempus quam pellentesque nec. Risus quis varius quam quisque id.\n" +
                "\n" +
                "Amet commodo nulla facilisi nullam. Fermentum dui faucibus in ornare quam. Elit scelerisque mauris pellentesque pulvinar. Mollis nunc sed id semper risus in. Et ligula ullamcorper malesuada proin libero nunc consequat interdum varius. Bibendum enim facilisis gravida neque convallis a cras semper auctor. Sit amet nulla facilisi morbi. Nam aliquam sem et tortor consequat id porta nibh. Quisque egestas diam in arcu cursus euismod. Porta non pulvinar neque laoreet suspendisse interdum consectetur libero. Sed nisi lacus sed viverra tellus in hac habitasse platea. Purus sit amet luctus venenatis lectus magna. Faucibus interdum posuere lorem ipsum. At elementum eu facilisis sed odio morbi quis. Montes nascetur ridiculus mus mauris vitae ultricies leo integer malesuada. Sagittis nisl rhoncus mattis rhoncus. Lectus sit amet est placerat.\n" +
                "\n" +
                "Malesuada fames ac turpis egestas sed tempus urna. Facilisi nullam vehicula ipsum a arcu cursus vitae. Faucibus nisl tincidunt eget nullam non. Eget arcu dictum varius duis at consectetur lorem donec massa. Non tellus orci ac auctor augue mauris augue neque. Dictum at tempor commodo ullamcorper a lacus vestibulum. Ligula ullamcorper malesuada proin libero nunc consequat interdum. Feugiat nibh sed pulvinar proin. Nec nam aliquam sem et. In mollis nunc sed id semper. Interdum posuere lorem ipsum dolor sit.\n" +
                "\n" +
                "Aliquam purus sit amet luctus venenatis lectus magna fringilla. Etiam dignissim diam quis enim lobortis scelerisque fermentum. Turpis egestas integer eget aliquet nibh. Pharetra pharetra massa massa ultricies mi quis hendrerit dolor magna. Eget nunc scelerisque viverra mauris in aliquam sem fringilla ut. Dui nunc mattis enim ut tellus elementum. Purus sit amet luctus venenatis lectus. Suspendisse interdum consectetur libero id faucibus nisl tincidunt. Integer enim neque volutpat ac tincidunt. Lacus vel facilisis volutpat est velit. Diam maecenas ultricies mi eget mauris pharetra et ultrices. Lacus luctus accumsan tortor posuere ac ut consequat.\n" +
                "\n" +
                "Faucibus et molestie ac feugiat sed lectus vestibulum. Mattis nunc sed blandit libero volutpat sed cras. Sed augue lacus viverra vitae congue. Pharetra sit amet aliquam id diam maecenas ultricies. Metus dictum at tempor commodo ullamcorper a. Viverra aliquet eget sit amet tellus. Odio ut sem nulla pharetra diam sit amet nisl suscipit. Tincidunt vitae semper quis lectus nulla at volutpat diam. Dolor sed viverra ipsum nunc. Phasellus faucibus scelerisque eleifend donec pretium vulputate. Id eu nisl nunc mi. Ut eu sem integer vitae justo eget magna. Nisl purus in mollis nunc sed id semper. Velit ut tortor pretium viverra. Ut consequat semper viverra nam libero.</string>\n"

}