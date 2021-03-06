package server.session

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
import javax.inject.Inject

class LoginController @Inject constructor(
    private val loginService: LoginService
) {
    fun initRoutes(app: Javalin) {
        app.routes {
            path("api") {
                post("login", this::login)
                post("logout", this::logout)
            }
        }
    }

    fun login(context: Context) {
        val body = context.bodyAsClass(LoginRequest::class.java)
        val session = loginService.login(body)
        context.cookie("sid", session.token)
        context.json(mapOf("success" to true))
    }

    fun logout(context: Context) {
        context.removeCookie("sid")
        context.status(200)
        context.json(mapOf("success" to true))
    }
}
