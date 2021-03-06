package server.user

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context
import server.error.UnauthenticatedError
import server.session.AuthHandler
import java.util.*
import javax.inject.Inject

class UserController @Inject constructor(
    private val createUserService: CreateUserService,
    private val fetchUserService: FetchUserService,
    private val authHandler: AuthHandler
) {
    fun initRoutes(app: Javalin) {
        app.before("/api/users/me", authHandler)
        app.routes {
            path("api/users") {
                get("me", this::getUser)
                post(this::createUser)
            }
        }
    }

    fun createUser(context: Context) {
        val body = context.bodyAsClass(CreateUserRequest::class.java)
        context.status(201)
        context.json(createUserService.createUser(body))
    }

    fun getUser(context: Context) {
        val id = context.attribute<UUID>("userId")!! // TODO: I don't like this.
        val user = fetchUserService.findUser(id)
            ?: throw UnauthenticatedError()
        context.json(user)
    }
}
