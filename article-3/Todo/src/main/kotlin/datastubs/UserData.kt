package datastubs


import api.TodoList
import api.User
import api.UserPayLoad


class UserData {
    companion object Data {
        val users = mutableListOf(
            User("/user/1", 1, "Alice", listOf()),
            User("/user/2", 2, "Bob", listOf())
        )
    }

    fun find(id : Int): User? {
        return users.find { it.id == id }
    }

    fun findAll(): List<User> {
        return users
    }

    fun findTodoList(id: Int): List<TodoList>? {
        return users.find { it.id == id }?.todoLists
    }

    fun create(userPayload: UserPayLoad): User {
        val id = users.last().id + 1
        val user = User("/user/$id", id, userPayload.username, emptyList())

        users.add(user)

        return user
    }
}




