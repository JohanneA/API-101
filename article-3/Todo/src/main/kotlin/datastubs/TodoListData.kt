package datastubs

import api.TodoList
import api.TodoListPayload
import java.time.LocalDate

class TodoListData {

    companion object Data {
        val todoLists = mutableListOf<TodoList>(
            TodoList("/todo-lists/1", 1, "Weekly todos", 1,
                UserData.users[0].self, LocalDate.now(), listOf()),
            TodoList("/todo-lists/2", 2, "Blog todos", 1,
                UserData.users[0].self, LocalDate.now(), listOf())
        )
    }

    fun find(id : Int): TodoList? {
        return todoLists.find { it.id == id }
    }

    fun findAll(): List<TodoList>? {
        return todoLists
    }

    fun create(todoListPayload: TodoListPayload): TodoList {
        val id = todoLists.last().id + 1
        val list = TodoList("/todo-lists/$id", id, todoListPayload.name, 1,
                UserData.users[0].self, LocalDate.now(), listOf())

        todoLists.add(list)

        return list
    }

    fun remove(id: Int) {
        todoLists.removeIf { it.id == id }
    }

    fun update(id: Int, update: TodoListPayload): TodoList? {
        todoLists.find { it.id == id }?.name = update.name
        return todoLists.find { it.id == id }
    }


}