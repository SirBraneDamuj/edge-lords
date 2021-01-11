package model.game

sealed class GameCard {
    abstract val id: String
    abstract val cardName: String
    abstract var manaCost: Int

    fun toCopy(): GameCard =
        when (this) {
            is GameNatialCard -> this.copy()
            is GameSpellCard -> this.copy()
            is GameMasterCard -> this.copy()
        }
}

data class GameMasterCard(
    override val id: String,
    override val cardName: String
) : GameCard() {
    override var manaCost: Int
        get() = -1
        set(_) {
            error("you weren't supposed to do that")
        }
}

data class GameNatialCard(
    override val id: String,
    override val cardName: String,
    override var manaCost: Int
) : GameCard() {
}

data class GameSpellCard(
    override val id: String,
    override val cardName: String,
    override var manaCost: Int
) : GameCard() {
}

