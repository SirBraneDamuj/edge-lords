package server.session

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.`java-time`.datetime
import server.user.User
import server.user.Users
import java.util.*

object Sessions : UUIDTable() {
    val token = varchar("token", 64).uniqueIndex()
    val expiresAt = datetime("expires_at")
    val user = reference("user", Users).index()
}

class Session(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Session>(Sessions)

    var token by Sessions.token
    var expiresAt by Sessions.expiresAt
    var user by User referencedOn Sessions.user
}
