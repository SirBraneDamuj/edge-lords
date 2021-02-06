package server.game

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context
import server.session.AuthHandler
import javax.inject.Inject

class GameController @Inject constructor(
    private val startGameService: StartGameService,
    private val fetchGameService: FetchGameService,
    private val gameActionService: GameActionService,
    private val authHandler: AuthHandler
) {
    fun initRoutes(app: Javalin) {
        app.before("/games/*", authHandler)
        app.routes {
            path("games") {
                post(this::createGame)
                path(":gameId") {
                    get(this::getGame)
                    put(this::doAction)
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

    fun doAction(context: Context) {
        val gameId = context.pathParam("gameId").toInt()
        val actionDto = context.bodyAsClass(ActionDto::class.java)
        val gamePerspective = gameActionService.performAction(
            gameId, context.attribute<Int>("userId")!!.toString(), actionDto
        )
        context.json(gamePerspective)
    }
}
