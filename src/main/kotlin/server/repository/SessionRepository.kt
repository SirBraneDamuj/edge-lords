package server.repository

import org.jetbrains.exposed.sql.transactions.transaction
import server.config.Db
import server.dto.SessionDto
import server.error.InvalidSessionError
import server.error.UnauthenticatedError
import server.model.Session
import server.model.Sessions
import server.model.User
import java.time.LocalDateTime
import javax.inject.Inject

class SessionRepository @Inject constructor(
    private val db: Db
) {

    fun getSessionByToken(
        token: String
    ): SessionDto = transaction {
        val session = Session
            .find {
                Sessions.token eq token
            }
            .singleOrNull()
            ?: throw InvalidSessionError()
        SessionDto(
            token = session.token,
            expiresAt = session.expiresAt,
            userId = session.user.id.value
        )
    }

    fun createSession(
        token: String,
        userId: Int,
        expiresAt: LocalDateTime
    ): SessionDto = transaction {
        val user = User.findById(userId) ?: throw UnauthenticatedError()
        val session = Session.new {
            this.token = token
            this.expiresAt = expiresAt
            this.user = user
        }
        SessionDto(
            token = session.token,
            expiresAt = session.expiresAt,
            userId = session.user.id.value
        )
    }

}
