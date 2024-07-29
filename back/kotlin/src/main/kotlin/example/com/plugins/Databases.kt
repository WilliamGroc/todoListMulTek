package example.com.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.*
import kotlinx.coroutines.*

fun Application.configureDatabases() {
    val dbConnection: Connection = connectToPostgres()
    val todoService = TodoService(dbConnection)
    
    install(ContentNegotiation) {
        json()
    }

    routing {
    
        // Create todo
        post("/todo") {
            val todo = call.receive<Todo>()
            val id = todoService.create(todo)
            call.respond(HttpStatusCode.Created, id)
        }
    
        // Read todo
        get("/todo/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            try {
                val todo = todoService.read(id)
                call.respond(HttpStatusCode.OK, todo)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    
        // Update todo
        put("/todo/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            val user = call.receive<Todo>()
            todoService.update(id, user)
            call.respond(HttpStatusCode.OK)
        }
    
        // Delete todo
        delete("/todo/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            todoService.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}

/**
 * Makes a connection to a Postgres database.
 *
 * In order to connect to your running Postgres process,
 * please specify the following parameters in your configuration file:
 * - postgres.url -- Url of your running database process.
 * - postgres.user -- Username for database connection
 * - postgres.password -- Password for database connection
 *
 * If you don't have a database process running yet, you may need to [download]((https://www.postgresql.org/download/))
 * and install Postgres and follow the instructions [here](https://postgresapp.com/).
 * Then, you would be able to edit your url,  which is usually "jdbc:postgresql://host:port/database", as well as
 * user and password values.
 *
 *
 * @param embedded -- if [true] defaults to an embedded database for tests that runs locally in the same process.
 * In this case you don't have to provide any parameters in configuration file, and you don't have to run a process.
 *
 * @return [Connection] that represent connection to the database. Please, don't forget to close this connection when
 * your application shuts down by calling [Connection.close]
 * */
fun Application.connectToPostgres(): Connection {
    Class.forName("org.postgresql.Driver")
    val url = environment.config.property("postgres.url").getString()
    val user = environment.config.property("postgres.user").getString()
    val password = environment.config.property("postgres.password").getString()

    return DriverManager.getConnection(url, user, password)
}
