package api

import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import java.time.LocalDate
import java.time.LocalDateTime

fun Routing.todoItem() {

    get("/todo-items") {
        call.respondText("Todo item!!")
    }
}

data class TodoItemPayload(
    val title: String?,
    val description: String?,
    val deadline: LocalDateTime?,
    val parentId: Int?,
    val completed: Boolean?
)

data class TodoItems(
    val todoItems: List<TodoItem>
)

data class TodoItem(
    val self: String,
    val id: Long,
    val ownerId: Int,
    val ownerLink: String,
    var parentId: Int,
    var parentLink: String,
    var title: String,
    var description: String,
    val createdDate: LocalDate,
    var deadline: LocalDateTime,
    var completed: Boolean
)