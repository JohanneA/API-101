package api

import com.google.gson.JsonSyntaxException
import datastubs.TodoItemRepository
import datastubs.TodoListRepository
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import java.time.LocalDate
import java.util.*

fun Routing.todoItem() {
    val todoItemRepository = TodoItemRepository()

    route("$API_PREFIX/todo-items") {
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
            try {
                val body = call.receive<TodoItemPayload>()

                if(body.validate()) {
                    call.response.status(HttpStatusCode.Created)
                    call.respond(todoItemRepository.create(body))
                } else {
                    call.respond(HttpStatusCode.NotFound, "No todo list with id: ${body.parentId}")
                }


            } catch (e: JsonSyntaxException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON: ${e.cause?.message}")
            }
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

            try {
                val body = call.receive<TodoItemPayload>()
                val list = todoItemRepository.update(id, body)

                if (list != null) {
                    call.respond(list)
                } else {
                    call.response.status(HttpStatusCode.NotFound)
                }
            } catch(e: JsonSyntaxException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON: ${e.cause?.message}")
            }
        }
    }
}

data class TodoItemPayload(
    val title: String?,
    val description: String?,
    val deadline: Date?,
    val parentId: Int?,
    val completed: Boolean?
) {

    fun validate(): Boolean {
        val list = TodoListRepository.todoLists
        return list.any { it.id == this.parentId }
    }
}

data class TodoItem(
    val self: String,
    val id: Long,
    val userId: Int,
    val userLink: String,
    var parentId: Int,
    var parentLink: String,
    var title: String,
    var description: String,
    val createdDate: Date,
    var deadline: Date,
    var completed: Boolean
)
