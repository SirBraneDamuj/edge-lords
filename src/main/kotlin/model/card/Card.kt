package model.card

import kotlinx.serialization.Serializable
import model.Element
import model.Range
import model.Speed
import model.game.EffectTargetingMode

interface Card {
    val name: String
    val cardType: CardType
}

enum class CardType {
    NATIAL,
    SPELL,
    MASTER;
}

@Serializable
data class MasterCard(
    override val name: String,
    val mana: Int,
    val attack: Int,
    val hp: Int,
    val range: Range,
    val skillText: String,
    val skillManaCost: Int,
    val abilityText: String
) : Card {
    override val cardType = CardType.MASTER
}

@Serializable
data class NatialCard(
    override val name: String,
    val manaCost: Int,
    val attack: Int,
    val hp: Int,
    val range: Range,
    val speed: Speed,
    val element: Element,
    val skillText: String,
    val abilityText: String,
) : Card {
    override val cardType = CardType.NATIAL
}

@Serializable
data class SpellCard(
    override val name: String,
    val manaCost: Int,
    val effectText: String,
    val targetingMode: EffectTargetingMode
) : Card {
    override val cardType = CardType.SPELL
}
