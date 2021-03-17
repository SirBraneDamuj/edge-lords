package server.session

import java.util.*

data class Credentials(
    val userId: UUID,
    val passwordHash: String
)
