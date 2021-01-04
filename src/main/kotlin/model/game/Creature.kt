package model.game

import kotlinx.serialization.Serializable
import model.Element
import model.Speed

sealed class Creature {
    abstract val card: GameCard
    abstract var position: Position
    abstract var activationState: ActivationState
    abstract var attack: Int
    abstract var hp: Int
    abstract var maxHp: Int
    abstract val element: Element?
    abstract var sealCount: Int
    abstract var guardCount: Int

    val sealed: Boolean
        get() = sealCount >= 0
    val guarded: Boolean
        get() = guardCount >= 0

    fun receiveDamage(damage: Int) {
        if (guarded) {
            guardCount--
        } else {
            hp -= damage
        }
    }
}

enum class Position {
    FRONT_ONE,
    FRONT_TWO,
    FRONT_THREE,
    FRONT_FOUR,
    BACK_ONE,
    BACK_TWO,
    BACK_THREE;
}

enum class ActivationState {
    NOT_READY,
    READY,
    MOVED,
    READY_AGAIN,
    ACTIVATED;
}

@Serializable
data class Master(
    override val card: GameMasterCard,
    override var position: Position,
    override var activationState: ActivationState,
    override var attack: Int,
    override var hp: Int,
    override var maxHp: Int,
    override var guardCount: Int = 0,
    override var sealCount: Int = 0
) : Creature() {
    override val element: Element? = null
}

@Serializable
data class Natial(
    override val card: GameNatialCard,
    override var position: Position,
    override var activationState: ActivationState,
    override var attack: Int,
    override var hp: Int,
    override var maxHp: Int,
    override val element: Element,
    override var guardCount: Int = 0,
    override var sealCount: Int = 0
) : Creature()

object Natials {
    fun summonFromCardToPosition(gameCard: GameNatialCard, position: Position, magicCrystal: Boolean) =
        Natial(
            card = gameCard,
            position = position,
            activationState = when (gameCard.card.speed) {
                Speed.NORMAL -> ActivationState.NOT_READY
                Speed.FAST -> ActivationState.READY
            },
            attack = gameCard.card.attack.let { if (magicCrystal) it + 1 else it },
            hp = gameCard.card.hp.let { if (magicCrystal) it + 1 else it },
            maxHp = gameCard.card.hp.let { if (magicCrystal) it + 1 else it },
            element = gameCard.card.element
        )
}

