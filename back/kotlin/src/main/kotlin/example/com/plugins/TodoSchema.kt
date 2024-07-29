package example.com.plugins

import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.sql.Connection
import java.sql.Statement

@Serializable
object Todo : Table<Nothing>("todo") {
    val id = int("id").primaryKey()
    val description = varchar("description")
    val checked = boolean("checked")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}

class TodoService(private val connection: Connection) {
    companion object {
        private const val SELECT_TODO_BY_ID = "SELECT title, description FROM todo WHERE id = ?"
        private const val INSERT_TODO = "INSERT INTO todo (title, description) VALUES (?, ?)"
        private const val UPDATE_TODO = "UPDATE todo SET title = ?, description = ? WHERE id = ?"
        private const val DELETE_TODO = "DELETE FROM todo WHERE id = ?"
    }

    private var newTodoId = 0

    // Create new todo
    suspend fun create(todo: Todo): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(INSERT_TODO, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, todo.title)
        statement.setInt(2, todo.description)
        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys
        if (generatedKeys.next()) {
            return@withContext generatedKeys.getInt(1)
        } else {
            throw Exception("Unable to retrieve the id of the newly inserted todo")
        }
    }

    // Read a todo
    suspend fun read(id: Int): Todo = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_TODO_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val title = resultSet.getString("title")
            val description = resultSet.getInt("description")
            return@withContext Todo(title, description)
        } else {
            throw Exception("Record not found")
        }
    }

    // Update a todo
    suspend fun update(id: Int, todo: Todo) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(UPDATE_TODO)
        statement.setString(1, todo.title)
        statement.setInt(2, todo.description)
        statement.setInt(3, id)
        statement.executeUpdate()
    }

    // Delete a todo
    suspend fun delete(id: Int) = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(DELETE_TODO)
        statement.setInt(1, id)
        statement.executeUpdate()
    }
}

