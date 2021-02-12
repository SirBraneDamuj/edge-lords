package server.user

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import server.deck.Deck
import server.deck.Decks
import server.game.Game
import server.game.GameDeck
import server.game.GameDecks

object Users : IntIdTable() {
    val name = varchar("name", 20).uniqueIndex()
    val passwordHash = varchar("password_hash", 64)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var name by Users.name
    var passwordHash by Users.passwordHash
    val decks by Deck referrersOn Decks.user
    val gameDecks by GameDeck referrersOn GameDecks.user
}

