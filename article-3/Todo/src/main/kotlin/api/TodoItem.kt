package api

import datastubs.TodoItemData
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*
import java.time.LocalDate
import java.time.LocalDateTime

fun Routing.todoItem() {
    val todoItemRepository = TodoItemData()

    route("/todo-items") {
        get("/") {
            call.respond(todoItemRepository.findAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]!!.toLong()

            val list = todoItemRepository.find(id)

            if (list != null) {
                call.respond(list)
            } else {
                call.response.status(HttpStatusCode.NotFound)
            }
        }

        post("/") {
            val body = call.receive<TodoItemPayload>()

            call.respond(todoItemRepository.create(body))
        }

        delete("/{id}") {
            val id = call.parameters["id"]!!.toLong()

            if (todoItemRepository.remove(id)) {
                call.response.status(HttpStatusCode.OK)
            } else {
                call.response.status(HttpStatusCode.NotFound)
            }
        }

        patch("/{id}") {
            val id = call.parameters["id"]!!.toLong()

            val body = call.receive<TodoItemPayload>()
            val list = todoItemRepository.update(id, body)

            if (list != null) {
                call.respond(list)
            } else {
                call.response.status(HttpStatusCode.NotFound)
            }
        }
    }
}

data class TodoItemPayload(
    val title: String?,
    val description: String?,
    val deadline: LocalDate?,
    val parentId: Int?,
    val completed: Boolean?
)

data class TodoItem(
    val self: String,
    val id: Long,
    val userId: Int,
    val userLink: String,
    var parentId: Int,
    var parentLink: String,
    var title: String,
    var description: String,
    val createdDate: LocalDate,
    var deadline: LocalDate,
    var completed: Boolean
)