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

data class TodoItems(
    val todoItems: List<TodoItem>
)

data class TodoItem(
    val self: String,
    val id: Long,
    val ownerId: String,
    val ownerLink: User,
    val parentId: String,
    val parentLink: TodoList,
    val title: String,
    val description: String,
    val createdDate: LocalDate,
    val deadline: LocalDateTime,
    val completed: Boolean
)