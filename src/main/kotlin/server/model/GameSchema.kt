package server.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Games : IntIdTable() {
    val deckOne = reference("deckOne", Decks)
    val deckTwo = reference("deckTwo", Decks)
    val state = text("state")
}

class Game(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Game>(Games)

    var state by Games.state
    var deckOne by Deck referencedOn(Games.deckOne)
    var deckTwo by Deck referencedOn(Games.deckTwo)
}

