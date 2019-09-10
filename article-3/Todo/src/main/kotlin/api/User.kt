package api

import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get

fun Routing.user() {

    get("/users") {
        call.respondText("user!!")
    }
}

data class UserPayLoad(
    val username: String,
    val email: String,
    val password: String
)

data class Users(
    val users: List<User>
)

data class User(
    val self: String,
    val id: Long,
    val name: String,
    val todoLists: List<TodoList>
)