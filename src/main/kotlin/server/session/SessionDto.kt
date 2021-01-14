package server.session

import java.time.LocalDateTime

data class SessionDto(
    val token: String,
    val expiresAt: LocalDateTime,
    val userId: Int
)
