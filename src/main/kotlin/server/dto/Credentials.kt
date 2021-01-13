package server.dto

data class Credentials(
    val userId: Int,
    val passwordHash: String
)
