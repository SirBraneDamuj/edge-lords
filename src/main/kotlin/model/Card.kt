package model

import kotlinx.serialization.Serializable

@Serializable
data class Master(
    val mana: Int,
    val attack: Int,
    val hp: Int,
    val maxHp: Int,
    val range: Range,
    val skillText: String,
    val abilityText: String,
    val abilityManaCost: Int,
    val activationState: ActivationState
)

@Serializable
data class Natial(
    override val name: String,
    override val manaCost: Int,
    val attack: Int,
    val hp: Int,
    val maxHp: Int,
    val range: Range,
    val speed: Speed,
    val element: Element,
    val skillText: String,
    val abilityText: String,
    val abilityState: AbilityState,
    val activationState: ActivationState
): Card {
    override val type = CardType.NATIAL
}

@Serializable
data class SpellCard(
    override val name: String,
    override val manaCost: Int,
    val effectText: String,
): Card {
    override val type = CardType.SPELL
}

interface Card {
    val name: String
    val type: CardType
    val manaCost: Int
}

enum class CardType {
    NATIAL,
    MASTER,
    SPELL;
}

@Serializable
data class CombatStats(
    val attack: Int,
    val hp: Int,
    val range: Range,
    val speed: Speed
)

enum class Element {
    FIRE,
    WIND,
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

enum class AbilityState {
    NONE,
    NOT_USED,
    USED;
}

enum class ActivationState {
    NOT_ACTIVATED,
    MOVED,
    SECOND_ACTIVATION_READY,
    ACTIVATED;
}
