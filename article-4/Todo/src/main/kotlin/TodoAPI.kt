import api.todoItem
import api.todoList
import api.user
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.text.DateFormat

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            gson {
                setDateFormat(DateFormat.SHORT)
                setPrettyPrinting()
            }
        }
        routing {
            get("/") {
                call.respondText("Todo API", ContentType.Text.Html)
            }

            user()

            todoList()

            todoItem()
        }
    }.start(wait = true)
}