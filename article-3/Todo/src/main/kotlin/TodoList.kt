import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import java.time.LocalDate

fun Routing.todoList() {

    get("/todo-lists") {
        call.respondText("Todo list!!")
    }
}

data class TodoLists(
    val todoLists: List<TodoList>
)

data class TodoList(
    val self: String,
    val id: Long,
    val name: String,
    val ownerId: String,
    val ownerLink: User,
    val createdDate: LocalDate,
    val todoItems: List<TodoItem>
)