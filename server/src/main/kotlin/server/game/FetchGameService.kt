package server.game

import com.fasterxml.jackson.databind.ObjectMapper
import model.game.Game
import server.error.RecordNotFoundError
import javax.inject.Inject

class FetchGameService @Inject constructor(
    private val gameRepository: GameRepository,
    private val gamePerspectiveService: GamePerspectiveService,
    private val objectMapper: ObjectMapper
) {
    fun getGamePerspectiveForUser(
        id: Int,
        playerId: Int
    ): GamePerspective {
        val game = gameRepository.findGame(id)
            ?: throw RecordNotFoundError()
        val gameState = objectMapper.readValue(game.state, Game::class.java)
        return gamePerspectiveService.buildPerspective(id, playerId, gameState)
    }
}
