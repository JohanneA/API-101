package datastubs

import api.TodoList
import api.TodoListPayload
import java.time.LocalDate

class TodoListData {

    private val todoLists = mutableListOf<TodoList>(
        TodoList("/todo-lists/1", 1, "Weekly todos", "/users/1",
            UserData().users[0], LocalDate.now(), listOf()),
        TodoList("/todo-lists/2", 2, "Blog todos", "/users/1",
            UserData().users[0], LocalDate.now(), listOf())
    )

    fun find(id : Long): TodoList? {
        return todoLists.find { it.id == id }
    }

    fun findAll(): List<TodoList>? {
        return todoLists
    }

    fun create(todoListPayload: TodoListPayload): TodoList {
        val id = todoLists.last().id + 1
        return TodoList("/todo-lists/$id", id, todoListPayload.name, UserData().users[0].self,
                UserData().users[0], LocalDate.now(), listOf())
    }

    fun remove(id: Long) {
        todoLists.removeIf { it.id == id }
    }

    fun update(id: Long, update: TodoListPayload): TodoList? {
        todoLists.find { it.id == id }?.name = update.name
        return todoLists.find { it.id == id }
    }


}