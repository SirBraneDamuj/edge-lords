package model.game

data class Player(
    val name: String,
    val playerLabel: PlayerLabel,
    val master: Master,
    val natials: List<Natial>,
    val magicCrystals: Set<Position>,
    val hand: List<GameCard>,
    val deck: List<GameCard>,
    val discard: List<GameCard>,
    val mana: Int,
    val maxMana: Int
) {
    fun natialAtPosition(position: Position) =
        natials.singleOrNull { it.position == position }
}

enum class PlayerLabel {
    FIRST,
    SECOND;

    val other: PlayerLabel
        get() = when (this) {
            FIRST -> SECOND
            SECOND -> FIRST
        }
}
