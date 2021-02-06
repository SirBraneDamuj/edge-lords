package server.game

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
import server.session.AuthHandler
import javax.inject.Inject

class GameController @Inject constructor(
    private val startGameService: StartGameService,
    private val fetchGameService: FetchGameService,
    private val authHandler: AuthHandler
) {
    fun initRoutes(app: Javalin) {
        app.before("/games/*", authHandler)
        app.routes {
            path("games") {
                post(this::createGame)
                path(":gameId") {
                    ApiBuilder.get(this::getGame)
                }
            }
        }
    }

    fun createGame(context: Context) {
        val body = context.bodyAsClass(StartGameRequest::class.java)
        val deckIds = body.deckIds[0] to body.deckIds[1]
        val createdGame = startGameService.startGame(deckIds)
        context.status(201)
        context.json(createdGame)
    }

    fun getGame(context: Context) {
        val gameId = context.pathParam("gameId").toInt()
        val gamePerspective = fetchGameService.getGamePerspectiveForUser(
            id = gameId,
            playerId = context.attribute<Int>("userId")!!
        )
        context.json(gamePerspective)
    }
}
