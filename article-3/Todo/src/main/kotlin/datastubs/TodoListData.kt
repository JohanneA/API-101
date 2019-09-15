package datastubs

import api.TodoItem
import api.TodoList
import api.TodoListPayload
import api.User
import java.time.LocalDate
import java.time.LocalDateTime

class TodoListData {

    companion object Data {
        private val user: User = UserData.users[0]

        val todoLists = mutableListOf<TodoList>(
            TodoList("/todo-lists/1", 1, "Weekly todos", 1,
                user.self, LocalDate.now(), listOf(
                    TodoItem("/todo-items/1", 1, 1, UserData.users[0].self,
                    1, "/todo-lists/1", "Go to the gym",
                    "Do upper body workout", LocalDate.now(), LocalDate.now(), false)
                ,
                TodoItem("/todo-items/2", 2, 1,  UserData.users[0].self, 1,
                    "/todo-lists/1", "Grocery shopping",
                    "Get carrots", LocalDate.now(), LocalDate.now(), false)
                )
            ),
            TodoList("/todo-lists/2", 2, "Blog todos", 1,
                user.self, LocalDate.now(), listOf(
                    TodoItem("/todo-items/3", 3, 1, UserData.users[0].self,
                        2, "/todo-lists/2", "Create illustrations",
                        "Figure out the illustration and do it", LocalDate.now(), LocalDate.now(), false)
                ))
        )
    }

    fun find(id : Int): TodoList? {
        return todoLists.find { it.id == id }
    }

    fun findAll(): List<TodoList> {
        return todoLists
    }

    fun create(todoListPayload: TodoListPayload): TodoList {
        val id = todoLists.last().id + 1
        val list = TodoList("/todo-lists/$id", id, todoListPayload.name, 1,
                UserData.users[0].self, LocalDate.now(), listOf())

        todoLists.add(list)

        return list
    }

    fun remove(id: Int): Boolean {
        return todoLists.removeIf { it.id == id }
    }

    fun update(id: Int, update: TodoListPayload): TodoList? {
        todoLists.find { it.id == id }?.name = update.name
        return todoLists.find { it.id == id }
    }


}