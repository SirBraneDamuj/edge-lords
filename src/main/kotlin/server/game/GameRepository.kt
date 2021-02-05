package server.game

import org.jetbrains.exposed.sql.transactions.transaction
import server.deck.Deck
import server.error.RecordNotFoundError
import javax.inject.Inject

class GameRepository @Inject constructor() {
    fun findGame(id: Int) = transaction {
        Game.findById(id)
            ?.let {
                GameDto(
                    id = id,
                    deckOneId = it.deckOne.id.value,
                    deckTwoId = it.deckTwo.id.value,
                    playerOneId = it.deckOne.user.id.value,
                    playerTwoId = it.deckTwo.user.id.value,
                    state = it.state
                )
            }
    }

    fun createGame(
        deckIds: Pair<Int, Int>,
        gameState: String
    ) = transaction {
        val game = Game.new {
            this.deckOne = Deck.findById(deckIds.first) ?: throw RecordNotFoundError()
            this.deckTwo = Deck.findById(deckIds.second) ?: throw RecordNotFoundError()
            this.state = gameState
        }
        CreatedGame(id = game.id.value)
    }
}
