import api.todoItem
import api.todoList
import api.user
import auth.JwtConfig
import com.auth0.jwk.JwkProviderBuilder
import datastubs.UserRepository
import io.ktor.application.*
import io.ktor.auth.Authentication
import io.ktor.auth.UserPasswordCredential
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.*
import io.ktor.request.receive
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

        val userSource = UserRepository()
        install(Authentication) {
            /**
             * Setup the JWT authentication to be used in [Routing].
             * If the token is valid, the corresponding [User] is fetched from the database.
             * The [User] can then be accessed in each [ApplicationCall].
             */
            jwt {
                verifier(JwtConfig.verifier)
                realm = "ktor.io"
                validate {
                    it.payload.getClaim("id").asInt()?.let(userSource::find)
                }
            }
        }

        routing {
            get("/") {
                call.respondText("Todo API", ContentType.Text.Html)
            }

            post("/login") {
                val credentials = call.receive<UserPasswordCredential>()
                val user = userSource.findByCredentials(credentials)
                val token = JwtConfig.makeToken(user)
                call.respondText(token)
            }

            user()

            todoList()

            todoItem()
        }
    }.start(wait = true)
}