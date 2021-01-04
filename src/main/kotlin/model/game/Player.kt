package model.game

data class Player(
    val name: String,
    val playerLabel: PlayerLabel,
    var creatures: List<Creature>,
    var magicCrystals: Set<Position>,
    var hand: List<GameCard>,
    var deck: List<GameCard>,
    var discard: List<GameCard>,
    var mana: Int,
    var maxMana: Int
) {
    fun creatureAtPosition(position: Position) =
        creatures.singleOrNull { it.position == position }
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
