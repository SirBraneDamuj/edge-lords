package server.model

data class User(
    val id: String,
    val name: String,
    val decks: List<Deck>
)

data class Deck(
    val id: String,
    val name: String,
    val master: String,
    val cards: Map<String, Int>
)
