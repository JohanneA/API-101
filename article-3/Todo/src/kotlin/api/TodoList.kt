package api

import datastubs.TodoListRepository
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import java.time.LocalDate

fun Routing.todoList() {
    val todoListRepository = TodoListRepository()

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
            val body = call.receive<TodoListPayload>()

            call.response.status(HttpStatusCode.Created)
            call.respond(todoListRepository.create(body))
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
            val id = call.parameters["id"]!!.toInt()

            val body = call.receive<TodoListPayload>()
            val list = todoListRepository.update(id, body)

            if (list != null) {
                call.respond(list)
            } else {
                call.response.status(HttpStatusCode.NotFound)
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
    val createdDate: LocalDate,
    val todoItems: List<TodoItem>
)