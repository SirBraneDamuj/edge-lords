package server.game

import java.util.*
import javax.inject.Inject

class GameListService @Inject constructor(
    private val gameRepository: GameRepository
) {
    fun listGamesForUser(userId: UUID) = GameList(gameRepository.listGamesByUserId(userId))
}