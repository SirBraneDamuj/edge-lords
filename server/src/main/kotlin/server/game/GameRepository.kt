package server.game

import model.game.PlayerLabel
import org.jetbrains.exposed.sql.transactions.transaction
import server.deck.Deck
import server.error.RecordNotFoundError
import server.user.User
import java.util.*
import javax.inject.Inject

class GameRepository @Inject constructor() {
    fun findGame(id: UUID) = transaction {
        Game.findById(id)
            ?.let { game ->
                val (deckOne, deckTwo) = game.gameDecks.map { gameDeck -> gameDeck.deck }
                GameDto(
                    id = id,
                    deckOneId = deckOne.id.value,
                    deckTwoId = deckTwo.id.value,
                    playerOneId = deckOne.user.id.value,
                    playerOneName = deckOne.user.name,
                    playerTwoId = deckTwo.user.id.value,
                    playerTwoName = deckTwo.user.name,
                    state = game.state
                )
            }
    }

    fun createGame(
        deckIds: Pair<UUID, UUID>,
        gameState: String
    ) = transaction {
        val game = Game.new {
            // EW GROSS
            this.state = gameState
        }
        val deckOne = Deck.findById(deckIds.first) ?: throw RecordNotFoundError()
        val deckTwo = Deck.findById(deckIds.second) ?: throw RecordNotFoundError()
        GameDeck.new {
            deck = deckOne
            user = deckOne.user
            label = PlayerLabel.FIRST.toString()
            this.game = game
        }
        GameDeck.new {
            deck = deckTwo
            user = deckTwo.user
            label = PlayerLabel.SECOND.toString()
            this.game = game
        }
        CreatedGame(id = game.id.value)
    }

    fun saveGame(
        gameId: UUID,
        gameState: String
    ) = transaction {
        val game = Game.findById(gameId)
            ?: throw RecordNotFoundError()
        game.state = gameState
    }

    fun listGamesByUserId(
        userId: UUID
    ) = transaction {
        val user = User.findById(userId)
            ?: throw RecordNotFoundError()
        user.gameDecks.map { gameDeck ->
            val game = gameDeck.game
            val deck = gameDeck.deck
            GameListEntry(
                id = game.id.value,
                deckId = deck.id.value,
                deckName = deck.name,
                opponentName = game.gameDecks.single { it.user.id.value != userId }.user.name,
                state = GameProgressState.IN_PROGRESS
            )
        }
    }
}
