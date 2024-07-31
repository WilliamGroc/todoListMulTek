package multek.com.models

import kotlinx.serialization.Contextual
import org.ktorm.entity.Entity
import java.time.LocalDateTime

interface Todo: Entity<Todo>{
    val id: Int
    val title: String
    val description: String
    val checked: Boolean
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
}
