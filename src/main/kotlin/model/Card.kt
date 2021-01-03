package model

import kotlinx.serialization.Serializable

data class MasterCard(
    val name: String,
    val attack: Int,
    val hp: Int,
)

interface Card {
    val name: String
    val type: CardType
}

enum class CardType {
    NATIAL,
    SPELL;
}

@Serializable
data class NatialCard(
    override val name: String,
    val attack: Int,
    val hp: Int,
    val manaCost: Int,
    val skillText: String,
    val abilityText: String,
    val element: Element
): Card {
    override val type = CardType.NATIAL
}

enum class Element {
    FIRE,
    WIND,
    GROUND,
    WATER;
}

@Serializable
data class SpellCard(
    override val name: String,
    val manaCost: Int,
    val effectText: String,
): Card {
    override val type = CardType.SPELL
}
