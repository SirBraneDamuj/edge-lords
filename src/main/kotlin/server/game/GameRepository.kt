package server.game

import com.fasterxml.jackson.databind.ObjectMapper
import org.jetbrains.exposed.sql.transactions.transaction
import server.deck.Deck
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val objectMapper: ObjectMapper
) {
    fun findGame(id: Int) =
        transaction {
            Game.findById(id)
        }

    fun createGame(
        deckOne: Deck,
        deckTwo: Deck,
        gameState: String
    ) {
        transaction {
            Game.new {
                this.deckOne = deckOne
                this.deckTwo = deckTwo
                this.state = gameState
            }
        }
    }
}
