package server.repository

import model.game.Game
import javax.inject.Inject

class GameRepository @Inject constructor() {
    private val games: MutableMap<String, Game> = mutableMapOf()

    fun getGame(gameId: String) =
        games[gameId]

    fun createGame(game: Game) {
        games[game.id] = game
    }
}
