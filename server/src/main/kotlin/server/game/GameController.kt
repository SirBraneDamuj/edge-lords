package server.game

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context
import server.error.RecordNotFoundError
import server.session.AuthHandler
import java.util.*
import javax.inject.Inject

class GameController @Inject constructor(
    private val startGameService: StartGameService,
    private val fetchGameService: FetchGameService,
    private val gameActionService: GameActionService,
    private val gameListService: GameListService,
    private val authHandler: AuthHandler
) {
    fun initRoutes(app: Javalin) {
        app.before("/api/games", authHandler)
        app.before("/api/games/*", authHandler)
        app.routes {
            path("api/games") {
                get(this::listGames)
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

    fun listGames(context: Context) {
        val userId = context.attribute<UUID>("userId")!!
        context.json(gameListService.listGamesForUser(userId))
    }

    fun getGame(context: Context) {
        val gameId = try {
            UUID.fromString(context.pathParam("gameId"))
        } catch (e: IllegalArgumentException) {
            throw RecordNotFoundError()
        }
        val gamePerspective = fetchGameService.getGamePerspectiveForUser(
            id = gameId,
            playerId = context.attribute<UUID>("userId")!!
        )
        context.json(gamePerspective)
    }

    fun doAction(context: Context) {
        val gameId = try {
            UUID.fromString(context.pathParam("gameId"))
        } catch (e: IllegalArgumentException) {
            throw RecordNotFoundError()
        }
        val actionDto = context.bodyAsClass(ActionDto::class.java)
        val gamePerspective = gameActionService.performAction(
            gameId, context.attribute<UUID>("userId")!!, actionDto
        )
        context.json(gamePerspective)
    }
}
