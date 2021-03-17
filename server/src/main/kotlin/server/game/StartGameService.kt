package server.game

import com.fasterxml.jackson.databind.ObjectMapper
import model.game.Games
import server.deck.Deck
import server.deck.DeckRepository
import server.deck.DeckService
import server.error.RecordNotFoundError
import java.util.*
import javax.inject.Inject

class StartGameService @Inject constructor(
    private val objectMapper: ObjectMapper,
    private val gameRepository: GameRepository,
    private val deckRepository: DeckRepository,
    private val deckService: DeckService,
) {
    fun startGame(
        deckIds: Pair<UUID, UUID>
    ): CreatedGame {
        val deckOne = deckRepository.findById(deckIds.first)
            ?: throw RecordNotFoundError()
        val deckTwo = deckRepository.findById(deckIds.second)
            ?: throw RecordNotFoundError()
        val state = objectMapper.writeValueAsString(
            Games.createGame(
                deck1 = deckService.constructDeck(deckOne),
                deck2 = deckService.constructDeck(deckTwo)
            )
        )
        return gameRepository.createGame(
            deckIds = deckIds,
            gameState = state
        )
    }
}
