package server.game

import com.fasterxml.jackson.databind.ObjectMapper
import model.game.Game
import server.error.RecordNotFoundError
import javax.inject.Inject

class FetchGameService @Inject constructor(
    private val gameRepository: GameRepository,
    private val objectMapper: ObjectMapper
) {
    fun getGameById(id: Int): GamePerspective {
        val game = gameRepository.findGame(id)
            ?: throw RecordNotFoundError()
        val gameState = objectMapper.readValue(game.state, Game::class.java)
        // TODO need to figure out whether I am player one or player two.
        // Also, I think I'm doing DAOs wrong.
        return GamePerspective(

        )
    }
}
