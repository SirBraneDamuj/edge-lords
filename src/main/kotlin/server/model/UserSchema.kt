package server.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {
    val name = varchar("name", 20).uniqueIndex()
    val passwordHash = varchar("password_hash", 64)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var name by Users.name
    var passwordHash by Users.passwordHash
    val decks by Deck referrersOn Decks.user
}

object Decks : IntIdTable() {
    val name = varchar("name", 30)
    val user = reference("user", Users)
    val master = varchar("master", 30)
}

class Deck(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Deck>(Decks)

    var name by Decks.name
    var user by User referencedOn Decks.user
    var master by Decks.master
    val cards by DeckCard referrersOn DeckCards.deck
}

object DeckCards : IntIdTable() {
    val name = varchar("name", 20)
    val count = integer("count")
    val deck = reference("deck", Decks)
}

class DeckCard(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DeckCard>(DeckCards)

    var name by DeckCards.name
    var count by DeckCards.count
    var deck by Deck referencedOn DeckCards.deck
}
