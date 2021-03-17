package server.deck

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import server.user.User
import server.user.Users
import java.util.*

object Decks : UUIDTable() {
    val name = varchar("name", 30)
    val user = reference("user", Users)
    val master = varchar("master", 30)
}

class Deck(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Deck>(Decks)

    var name by Decks.name
    var user by User referencedOn Decks.user
    var master by Decks.master
    val cards by DeckCard referrersOn DeckCards.deck
}

object DeckCards : UUIDTable() {
    val name = varchar("name", 20)
    val count = integer("count")
    val deck = reference("deck", Decks)
}

class DeckCard(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<DeckCard>(DeckCards)

    var name by DeckCards.name
    var count by DeckCards.count
    var deck by Deck referencedOn DeckCards.deck
}
