package multek.com.api.todo

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRoutesTodo() {
    val todoRepository by inject<TodoRepository>()

    install(ContentNegotiation) {
        json()
    }
    routing {
        route("/todo") {
            get {
                val result = todoRepository.findAll()
                call.respond(result)
            }

            get("/{id}") {
                val id = call.parameters["id"]

                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val result = todoRepository.findById(id.toInt())
                call.respond(result)
            }

            post {
                try {
                    val body = call.receive<TodoCreate>()
                    val result = todoRepository.save(body)

                    call.respond(result)
                } catch (ex: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            put("/{id}") {
                try {
                    val id = call.parameters["id"]
                    val body = call.receive<TodoUpdate>()

                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest)
                        return@put
                    }

                    val result = todoRepository.update(id.toInt(), body)

                    call.respond(result)
                } catch (ex: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/{id}") {
                try {
                    val id = call.parameters["id"]
                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest)
                        return@delete
                    }

                    todoRepository.delete(id.toInt())

                    call.respond(HttpStatusCode.OK)
                } catch (ex: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}


