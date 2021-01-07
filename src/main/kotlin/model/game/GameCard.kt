package model.game

import kotlinx.serialization.Serializable

@Serializable
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

@Serializable
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

@Serializable
data class GameNatialCard(
    override val id: String,
    override val cardName: String,
    override var manaCost: Int
) : GameCard() {
}

@Serializable
data class GameSpellCard(
    override val id: String,
    override val cardName: String,
    override var manaCost: Int
) : GameCard() {
}

