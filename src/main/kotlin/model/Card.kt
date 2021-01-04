package model

import kotlinx.serialization.Serializable

@Serializable
data class MasterCard(
    val name: String,
    val mana: Int,
    val attack: Int,
    val hp: Int,
    val range: Range,
    val skillText: String,
    val skillManaCost: Int,
    val abilityText: String
)

interface Card {
    val name: String
    val type: CardType
    val manaCost: Int
}

enum class CardType {
    NATIAL,
    SPELL;
}

@Serializable
data class NatialCard(
    override val name: String,
    override val manaCost: Int,
    val attack: Int,
    val hp: Int,
    val range: Range,
    val speed: Speed,
    val element: Element,
    val skillText: String,
    val abilityText: String,
) : Card {
    override val type = CardType.NATIAL
}

@Serializable
data class SpellCard(
    override val name: String,
    override val manaCost: Int,
    val effectText: String,
) : Card {
    override val type = CardType.SPELL
}

enum class Element {
    FIRE,
    HEAVEN,
    EARTH,
    WATER;
}

enum class Range {
    MELEE,
    RANGED;
}

enum class Speed {
    NORMAL,
    FAST;
}
