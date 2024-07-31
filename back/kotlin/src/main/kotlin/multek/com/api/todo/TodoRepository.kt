package multek.com.api.todo

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import multek.com.models.Todo
import multek.com.schema.TodoTable
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
data class TodoResponse(
    val id: Int?,
    val title: String?,
    val description: String?,
    val checked: Boolean?,
    @Contextual
    val updatedAt: LocalDateTime?,
    @Contextual
    val createdAt: LocalDateTime?
)

@Serializable
data class TodoCreate(
    val title: String,
    val description: String
)

@Serializable
data class TodoUpdate(
    val title: String,
    val description: String,
    val checked: Boolean
)

interface TodoRepository {
    suspend fun findAll(): List<TodoResponse>
    suspend fun findById(id: Int): TodoResponse
    suspend fun save(data: TodoCreate): TodoResponse
    suspend fun update(id: Int, data: TodoUpdate): TodoResponse
    suspend fun delete(id: Int)
}

data class TodoRepositoryImpl(val database: Database) : TodoRepository {
    override suspend fun findAll(): List<TodoResponse> {
        return database.from(TodoTable).select().map { row ->
            TodoResponse(
                id = row[TodoTable.id],
                title = row[TodoTable.title],
                description = row[TodoTable.description],
                checked = row[TodoTable.checked],
                createdAt = row[TodoTable.createdAt],
                updatedAt = row[TodoTable.updatedAt]
            )
        }
    }

    override suspend fun findById(id: Int): TodoResponse {
        return database.from(TodoTable).select().where {
            TodoTable.id eq id
        }.map { row ->
            TodoResponse(
                id = row[TodoTable.id],
                title = row[TodoTable.title],
                description = row[TodoTable.description],
                checked = row[TodoTable.checked],
                createdAt = row[TodoTable.createdAt],
                updatedAt = row[TodoTable.updatedAt]
            )
        }.first()
    }

    override suspend fun save(data: TodoCreate): TodoResponse {
        val insertedId = database.insert(TodoTable) {
            set(TodoTable.title, data.title)
            set(TodoTable.description, data.description)
            set(TodoTable.createdAt, LocalDateTime.now())
        }

        return findById(insertedId)
    }

    override suspend fun update(id: Int, data: TodoUpdate): TodoResponse {
        database.update(TodoTable) {
            set(TodoTable.title, data.title)
            set(TodoTable.description, data.description)
            set(TodoTable.checked, data.checked)
            set(TodoTable.updatedAt, LocalDateTime.now())
        }

        return findById(id)
    }

    override suspend fun delete(id: Int) {
        database.delete(TodoTable) {
            it.id eq id
        }
    }
}