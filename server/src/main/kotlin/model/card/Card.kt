package model.card

import model.Element
import model.Range
import model.Speed
import model.game.EffectTargetingMode

interface Card {
    val name: String
    val cardType: CardType
    val targetingMode: EffectTargetingMode
}

enum class CardType {
    NATIAL,
    SPELL,
    MASTER;
}

data class MasterCard(
    override val name: String,
    override val targetingMode: EffectTargetingMode,
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

data class NatialCard(
    override val name: String,
    override val targetingMode: EffectTargetingMode,
    val manaCost: Int,
    val attack: Int,
    val hp: Int,
    val range: Range,
    val speed: Speed,
    val element: Element,
    val skillText: String,
    val abilityText: String,
    val guardsNeighbors: Boolean
) : Card {
    override val cardType = CardType.NATIAL
}

data class SpellCard(
    override val name: String,
    override val targetingMode: EffectTargetingMode,
    val manaCost: Int,
    val effectText: String,
) : Card {
    override val cardType = CardType.SPELL
}
