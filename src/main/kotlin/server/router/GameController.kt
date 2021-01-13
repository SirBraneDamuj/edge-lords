package server.router

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
import io.javalin.http.Handler
import model.game.Games
import server.dto.CreateGameRequest
import server.dto.StartGameRequest
import server.repository.GameRepository
import server.service.StartGameService
import javax.inject.Inject

class GameController @Inject constructor(
    private val startGameService: StartGameService,
    private val gameRepository: GameRepository,
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
        val game = gameRepository.findGame(gameId)
        if (game == null) {
            context.status(404)
        } else {
            context.json(game)
        }
    }
}
