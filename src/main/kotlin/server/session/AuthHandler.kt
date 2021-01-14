package server.session

import io.javalin.http.Context
import io.javalin.http.Handler
import server.error.InvalidSessionError
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AuthHandler @Inject constructor(
    private val loginService: LoginService
) : Handler {
    override fun handle(ctx: Context) {
        val token = ctx.cookie("sid")
            ?: throw InvalidSessionError()
        val session = loginService.checkToken(token)
        if (session.expiresAt.isBefore(LocalDateTime.now(ZoneId.of("UTC")))) {
            ctx.redirect("/logout")
        } else {
            ctx.attribute("userId", session.userId)
        }
    }
}
