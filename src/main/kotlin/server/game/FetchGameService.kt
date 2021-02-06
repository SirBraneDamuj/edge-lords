package server.game

import com.fasterxml.jackson.databind.ObjectMapper
import model.game.Game
import server.error.RecordNotFoundError
import javax.inject.Inject

class FetchGameService @Inject constructor(
    private val gameRepository: GameRepository,
    private val objectMapper: ObjectMapper
) {
    fun getGamePerspectiveForUser(
        id: Int,
        playerId: Int
    ): GamePerspective {
        val game = gameRepository.findGame(id)
            ?: throw RecordNotFoundError()
        val gameState = objectMapper.readValue(game.state, Game::class.java)
        val me = gameState.players.values.single { it.id == playerId.toString() }
        val opponent = gameState.players.values.single { it.id != playerId.toString() }
        return GamePerspective(
            gameId = id,
            opponent = OpponentPerspective(
                name = opponent.name,
                handCount = opponent.hand.size,
                deckCount = opponent.deck.size,
                mana = opponent.mana,
                maxMana = opponent.maxMana,
                creatures = opponent.creatures
            ),
            self = SelfPerspective(
                name = me.name,
                deckCount = me.deck.size,
                hand = me.hand,
                mana = me.mana,
                maxMana = me.maxMana,
                creatures = me.creatures
            )
        )
    }
}
