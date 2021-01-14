package server.game

import com.fasterxml.jackson.databind.ObjectMapper
import model.game.Games
import server.deck.Deck
import server.deck.DeckService
import server.error.RecordNotFoundError
import javax.inject.Inject

class StartGameService @Inject constructor(
    private val objectMapper: ObjectMapper,
    private val deckService: DeckService
) {
    fun startGame(
        deckIds: Pair<Int, Int>
    ): CreatedGame {
        val deckOne = Deck.findById(deckIds.first)
            ?: throw RecordNotFoundError()
        val deckTwo = Deck.findById(deckIds.second)
            ?: throw RecordNotFoundError()
        val state = objectMapper.writeValueAsString(
            Games.createGame(
                deck1 = deckService.constructDeck(deckOne),
                deck2 = deckService.constructDeck(deckTwo)
            )
        )
        val game = Game.new {
            this.deckOne = deckOne
            this.deckTwo = deckTwo
            this.state = state
        }
        return CreatedGame(
            id = game.id.value
        )
    }
}
