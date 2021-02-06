package server.session

data class LoginRequest(
    val name: String,
    val password: String
)
