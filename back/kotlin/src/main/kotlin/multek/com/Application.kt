package multek.com

import io.ktor.server.application.*
import multek.com.api.todo.TodoRepository
import multek.com.api.todo.configureRoutesTodo
import multek.com.plugin.configureDatabases
import io.ktor.server.application.install
import multek.com.api.todo.TodoRepositoryImpl
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.main() {
    install(Koin) {
        val database = configureDatabases()

        val appModule = module {
            single<TodoRepository> { TodoRepositoryImpl(database) }
        }
        slf4jLogger()
        modules(appModule)
    }

    configureRoutesTodo()
}
