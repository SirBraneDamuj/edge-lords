package model

data class Deck(
    val name: String,
    val master: MasterCard,
    val cards: List<Card>
)
