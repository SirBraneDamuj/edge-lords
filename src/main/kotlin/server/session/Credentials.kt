package server.session

data class Credentials(
    val userId: Int,
    val passwordHash: String
)
