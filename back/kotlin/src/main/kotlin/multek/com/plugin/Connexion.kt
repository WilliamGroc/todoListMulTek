package multek.com.plugin

import io.ktor.server.application.Application
import org.ktorm.database.Database

fun Application.configureDatabases(): Database {
    return Database.connect(
        "jdbc:postgresql://localhost:5432/todolist",
        user = "postgres",
        password = "postgres"
    )
}