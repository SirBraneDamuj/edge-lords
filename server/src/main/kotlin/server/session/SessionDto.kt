package server.session

import java.time.LocalDateTime
import java.util.*

data class SessionDto(
    val token: String,
    val expiresAt: LocalDateTime,
    val userId: UUID
)
