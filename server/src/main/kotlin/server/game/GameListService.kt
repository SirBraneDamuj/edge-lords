package server.game

import javax.inject.Inject

class GameListService @Inject constructor(
    private val gameRepository: GameRepository
) {
    fun listGamesForUser(userId: Int) = GameList(gameRepository.listGamesByUserId(userId))
}