package model

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val name: String,
    val turnOrder: TurnOrder,
    val hand: List<Card>,
    val deck: List<Card>,
    val discard: List<Card>
)

enum class TurnOrder {
    FIRST,
    SECOND;
}

enum class Position(
    val frontRow: Boolean
) {
    ONE(true),
    TWO(true),
    THREE(true),
    FOUR(true),
    FIVE(false),
    SIX(false),
    SEVEN(false)
}

