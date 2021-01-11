package server.router

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
import model.game.Games
import server.dto.CreateGameRequest
import server.repository.GameRepository
import javax.inject.Inject

class GameController @Inject constructor(
    private val gameRepository: GameRepository
) {
    fun initRoutes(app: Javalin) {
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
        val game = Games.createFakeGame()
        gameRepository.createGame(game)
        context.json(game)
    }

    fun getGame(context: Context) {
        val gameId = context.pathParam("gameId")
        val game = gameRepository.getGame(gameId)
        if (game == null) {
            context.status(404)
        } else {
            context.json(game)
        }
    }
}
