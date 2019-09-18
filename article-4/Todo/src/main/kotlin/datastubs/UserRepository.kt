package datastubs


import api.TodoItem
import api.TodoList
import api.User
import api.UserPayLoad
import java.time.LocalDate
import java.util.*


class UserRepository {
    companion object Data {
        val users = mutableListOf(
            User("/user/1", 1, "Alice", listOf(

            TodoList("/todo-lists/1", 1, "Weekly todos", 1,
                "/user/1", Date(), listOf(
                    TodoItem("/todo-items/1", 1, 1, "/user/1",
                    1, "/todo-lists/1", "Go to the gym",
                    "Do upper body workout", Date(), Date(), false)
                ,
                TodoItem("/todo-items/2", 2, 1,  "/user/1", 1,
                    "/todo-lists/1", "Grocery shopping",
                    "Get carrots", Date(), Date(), false)
                )
            ),
            TodoList("/todo-lists/2", 2, "Blog todos", 1,
                "/user/1", Date(), listOf(
                    TodoItem("/todo-items/3", 3, 1, "/user/1",
                        2, "/todo-lists/2", "Create illustrations",
                        "Figure out the illustration and do it", Date(), Date(), false)
                )))),
            User("/user/2", 2, "Bob", listOf())
        )
    }

    fun find(id : Int): User? {
        return users.find { it.id == id }
    }

    fun findAll(): List<User> {
        return users
    }

    fun create(userPayload: UserPayLoad): User {
        val id = users.last().id + 1
        val user = User("/user/$id", id, userPayload.username, emptyList())

        users.add(user)

        return user
    }
}




