package server.user

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import server.deck.Deck
import server.deck.Decks
import server.game.Game
import server.game.GameDeck
import server.game.GameDecks
import java.util.*

object Users : UUIDTable() {
    val name = varchar("name", 20).uniqueIndex()
    val passwordHash = varchar("password_hash", 64)
}

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(Users)

    var name by Users.name
    var passwordHash by Users.passwordHash
    val decks by Deck referrersOn Decks.user
    val gameDecks by GameDeck referrersOn GameDecks.user
}

