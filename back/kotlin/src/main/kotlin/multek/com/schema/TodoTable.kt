package multek.com.schema

import multek.com.models.Todo
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.datetime
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object TodoTable: Table<Todo>("todo") {
    val id = int("id").primaryKey()
    val title = varchar("title")
    val description = varchar("description")
    val checked = boolean("checked")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}