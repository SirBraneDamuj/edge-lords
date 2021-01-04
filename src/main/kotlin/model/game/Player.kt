package model.game

data class Player(
    val name: String,
    val turnOrder: TurnOrder,
    val master: Master,
    val natials: List<Natial>,
    val magicCrystals: List<Position>,
    val hand: List<GameCard>,
    val deck: List<GameCard>,
    val discard: List<GameCard>,
    val mana: Int,
    val maxMana: Int
)

enum class TurnOrder {
    FIRST,
    SECOND;
}
