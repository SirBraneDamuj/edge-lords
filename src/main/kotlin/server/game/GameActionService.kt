package server.game

import com.fasterxml.jackson.databind.ObjectMapper
import javax.inject.Inject

class GameActionService @Inject constructor(
    private val gameRepository: GameRepository,
    private val objectMapper: ObjectMapper
) {
    fun performAction(
        gameId: Int,
        actionDto: ActionDto
    ) {
        val action = when {
        }
    }
}
