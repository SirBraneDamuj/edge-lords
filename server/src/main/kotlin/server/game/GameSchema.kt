package server.game

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import server.deck.Deck
import server.deck.Decks
import server.user.User
import server.user.Users
import java.util.*

object Games : UUIDTable() {
    val state = text("state")
}

class Game(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Game>(Games)

    val gameDecks by GameDeck referrersOn GameDecks.game
    var state by Games.state
}

object GameDecks : UUIDTable() {
    val game = reference("game", Games)
    val user = reference("user", Users)
    val deck = reference("deck", Decks)
    val label = varchar("label", 10)
}

class GameDeck(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<GameDeck>(GameDecks)

    var game by Game referencedOn (GameDecks.game)
    var user by User referencedOn (GameDecks.user)
    var deck by Deck referencedOn (GameDecks.deck)
    var label by GameDecks.label
}
