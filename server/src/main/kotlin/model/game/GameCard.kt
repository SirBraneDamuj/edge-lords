package model.game

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
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
    override var manaCost: Int = -1
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

