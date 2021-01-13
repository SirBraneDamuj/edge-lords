package server.repository

import org.jetbrains.exposed.sql.transactions.transaction
import server.config.Db
import server.dto.DeckDto
import server.error.RecordNotFoundError
import server.model.Deck
import server.model.DeckCard
import server.model.User
import javax.inject.Inject

class DeckRepository @Inject constructor(
    private val db: Db
) {
    fun createDeck(
        name: String,
        master: String,
        cards: Map<String, Int>,
        ownerId: Int
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
}
