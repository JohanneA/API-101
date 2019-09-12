package api

import datastubs.UserData
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route

fun Routing.user() {
    val userRepositoty = UserData()

    route("/users") {
        get("/") {
            call.respond(userRepositoty.findAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]!!.toInt()

            call.respond(userRepositoty.find(id)!!)
        }
    }
}

data class UserPayLoad(
    val username: String,
    val email: String,
    val password: String
)

data class User(
    val self: String,
    val id: Int,
    val name: String,
    val todoLists: List<TodoList>
)