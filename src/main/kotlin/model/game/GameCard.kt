package model.game

import kotlinx.serialization.Serializable
import model.card.*

interface GameCard {
    val card: Card
    val cardType: CardType
    var manaCost: Int
}

@Serializable
data class GameMasterCard(
    override val card: MasterCard
) : GameCard {
    override val cardType = CardType.MASTER
    override var manaCost: Int
        get() = -1
        set(_) {
            error("you weren't supposed to do that")
        }
}

@Serializable
data class GameNatialCard(
    override val card: NatialCard,
    override var manaCost: Int
) : GameCard {
    override val cardType = CardType.NATIAL
}

@Serializable
data class GameSpellCard(
    override val card: SpellCard,
    override var manaCost: Int
) : GameCard {
    override val cardType = CardType.SPELL
}

