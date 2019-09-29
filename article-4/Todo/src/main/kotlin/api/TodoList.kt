package api

import com.google.gson.JsonSyntaxException
import datastubs.TodoListRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import java.time.LocalDate
import java.util.*

fun Routing.todoList() {
    val todoListRepository = TodoListRepository()

    authenticate {
        route("$API_PREFIX/todo-lists") {
            get("/") {
                call.respond(todoListRepository.findAll())
            }

            get("/{id}") {
                val id = call.parameters["id"]!!.toInt()

                val list = todoListRepository.find(id)

                if (list != null) {
                    call.respond(list)
                } else {
                    call.response.status(HttpStatusCode.NotFound)
                }
            }

            post("/") {

                try {
                    val body = call.receive<TodoListPayload>()

                    call.response.status(HttpStatusCode.Created)
                    call.respond(todoListRepository.create(body))

                } catch (e: JsonSyntaxException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON: ${e.cause?.message}")
                }

            }

            delete("/{id}") {
                val id = call.parameters["id"]!!.toInt()

                if (todoListRepository.remove(id)) {
                    call.response.status(HttpStatusCode.OK)
                } else {
                    call.response.status(HttpStatusCode.NotFound)
                }
            }

            patch("/{id}") {

                try {
                    val id = call.parameters["id"]!!.toInt()

                    val body = call.receive<TodoListPayload>()
                    val list = todoListRepository.update(id, body)

                    if (list != null) {
                        call.respond(list)
                    } else {
                        call.response.status(HttpStatusCode.NotFound)
                    }

                } catch (e: JsonSyntaxException) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid JSON: ${e.cause?.message}")
                }
            }
        }
    }
}

data class TodoListPayload(
    val name: String
)

data class TodoList(
    val self: String,
    val id: Int,
    var name: String,
    val userId: Int,
    val userLink: String,
    val createdDate: Date,
    val todoItems: List<TodoItem>
)