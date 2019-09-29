package api

import com.google.gson.JsonSyntaxException
import datastubs.UserRepository
import io.ktor.application.call
import io.ktor.auth.Principal
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Routing.user() {
    val userRepository = UserRepository()

    authenticate {
        route("$API_PREFIX/users") {
            get("/") {
                call.respond(userRepository.findAll())
            }

            get("/{id}") {
                val id = call.parameters["id"]!!.toInt()

                val user = userRepository.find(id)

                if (user != null) {
                    call.respond(user)
                } else {
                    call.response.status(HttpStatusCode.NotFound)
                }
            }

            get("/{id}/todo-lists") {
                val id = call.parameters["id"]!!.toInt()

                val user = userRepository.find(id)

                if (user != null) {
                    call.respond(user.todoLists)
                } else {
                    call.response.status(HttpStatusCode.NotFound)
                }
            }

            post("/")  {
                try {
                    val body = call.receive<UserPayLoad>()

                    call.response.status(HttpStatusCode.Created)
                    call.respond(userRepository.create(body))

                } catch (e: JsonSyntaxException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON: ${e.cause?.message}")
                }
            }
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
) : Principal