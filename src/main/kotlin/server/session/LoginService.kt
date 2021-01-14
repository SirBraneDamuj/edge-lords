package server.session

import server.error.UnauthenticatedError
import server.user.UserRepository
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

class LoginService @Inject constructor(
    private val passwordService: PasswordService,
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) {
    fun login(
        loginRequest: LoginRequest
    ): SessionDto {
        val credentials = userRepository.findUserCredentials(loginRequest.name)
            ?: throw UnauthenticatedError()
        val authenticated = passwordService.checkPw(
            password = loginRequest.password,
            hashed = credentials.passwordHash
        )
        if (!authenticated) {
            throw UnauthenticatedError()
        }
        val expiresAt = LocalDateTime
            .now(ZoneId.of("UTC"))
            .plusDays(7)
        return sessionRepository.createSession(
            token = UUID.randomUUID().toString(),
            userId = credentials.userId,
            expiresAt = expiresAt
        )
    }

    fun checkToken(token: String) =
        sessionRepository.getSessionByToken(token)
}
