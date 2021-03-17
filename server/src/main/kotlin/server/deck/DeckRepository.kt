package server.deck

import org.jetbrains.exposed.sql.transactions.transaction
import server.config.Db
import server.error.UnauthorizedError
import server.error.RecordNotFoundError
import server.user.User
import java.util.*
import javax.inject.Inject

class DeckRepository @Inject constructor(
    private val db: Db
) {
    fun createDeck(
        name: String,
        master: String,
        cards: Map<String, Int>,
        ownerId: UUID
    ): DeckDto = transaction {
        val user = User.findById(ownerId)
            ?: throw RecordNotFoundError()
        val deck = Deck.new {
            this.name = name
            this.master = master
            this.user = user
        }
        cards.forEach { (cardName, count) ->
            DeckCard.new {
                this.name = cardName
                this.count = count
                this.deck = deck
            }
        }
        DeckDto.fromDeck(deck)
    }

    fun updateDeck(
        id: UUID,
        name: String,
        master: String,
        cards: Map<String, Int>,
        ownerId: UUID
    ): DeckDto = transaction {
        val deck = Deck.findById(id) ?: throw RecordNotFoundError()
        if (deck.user.id.value != ownerId) {
            throw UnauthorizedError()
        }
        deck.name = name
        deck.master = master
        deck.cards.forEach { it.delete() }
        cards.forEach { (cardName, count) ->
            DeckCard.new {
                this.name = cardName
                this.count = count
                this.deck = deck
            }
        }
        DeckDto.fromDeck(deck)
    }

    fun findById(id: UUID) = transaction {
        Deck.findById(id)
            ?.let {
                DeckDto.fromDeck(it)
            }
    }
}
