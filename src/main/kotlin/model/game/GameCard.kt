package model.game

import kotlinx.serialization.Serializable
import model.card.*

interface GameCard {
    val card: Card
    val cardType: CardType
    val manaCost: Int
}

@Serializable
data class GameMasterCard(
    override val card: MasterCard
) : GameCard {
    override val cardType = CardType.MASTER
    override val manaCost = -1
}

@Serializable
data class GameNatialCard(
    override val card: NatialCard,
    override val manaCost: Int
) : GameCard {
    override val cardType = CardType.NATIAL
}

@Serializable
data class GameSpellCard(
    override val card: SpellCard,
    override val manaCost: Int
) : GameCard {
    override val cardType = CardType.SPELL
}

