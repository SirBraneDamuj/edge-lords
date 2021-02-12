package server.game

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import server.deck.Deck
import server.deck.Decks
import server.user.User
import server.user.Users

object Games : IntIdTable() {
    val state = text("state")
}

class Game(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Game>(Games)

    val gameDecks by GameDeck referrersOn GameDecks.game
    var state by Games.state
}

object GameDecks : IntIdTable() {
    val game = reference("game", Games)
    val user = reference("user", Users)
    val deck = reference("deck", Decks)
    val label = varchar("label", 10)
}

class GameDeck(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GameDeck>(GameDecks)

    var game by Game referencedOn (GameDecks.game)
    var user by User referencedOn (GameDecks.user)
    var deck by Deck referencedOn (GameDecks.deck)
    var label by GameDecks.label
}
