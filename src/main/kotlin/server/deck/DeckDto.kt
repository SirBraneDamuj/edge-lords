package server.deck


data class DeckDto(
    val id: Int,
    val name: String,
    val master: String,
    val cards: Map<String, Int>
) {
    companion object {
        fun fromDeck(deck: Deck) =
            DeckDto(
                id = deck.id.value,
                name = deck.name,
                master = deck.master,
                cards = deck.cards.map { it.name to it.count }.toMap()
            )
    }
}

data class CreateDeckRequest(
    val name: String,
    val master: String,
    val cards: Map<String, Int>
)
