package model.game

import client.ActionInputException
import kotlinx.serialization.Serializable
import model.Cards
import model.Element
import model.Range
import model.Speed
import java.util.*

@Serializable
sealed class Creature {
    abstract val id: String
    abstract val card: GameCard
    abstract var position: Position
    abstract var activationState: ActivationState
    abstract var attack: Int
    abstract var hp: Int
    abstract var maxHp: Int
    abstract val range: Range
    abstract val speed: Speed
    abstract val element: Element?
    abstract var sealCount: Int
    abstract var guardCount: Int

    val sealed: Boolean
        get() = sealCount > 0
    val guarded: Boolean
        get() = guardCount > 0

    fun increaseMaxHp(amount: Int) {
        maxHp += amount
        heal(amount)
    }

    fun heal(amount: Int) {
        hp = (hp + amount).coerceAtMost(maxHp)
    }

    fun receiveDamage(damage: Int) {
        if (guarded) {
            guardCount--
        } else {
            hp -= damage
        }
    }

    fun toCopy() =
        when (this) {
            is Master -> this.copy()
            is Natial -> this.copy()
        }
}
// TODO: move position stuff to Glossary
val MASTER_STARTING_POSITION = Position.BACK_TWO

enum class Row {
    BACK,
    FRONT;

    fun positions() =
        when (this) {
            BACK -> Position.backPositions
            FRONT -> Position.frontPositions
        }

}

enum class Position(
    val row: Row
) {
    FRONT_ONE(Row.FRONT),
    FRONT_TWO(Row.FRONT),
    FRONT_THREE(Row.FRONT),
    FRONT_FOUR(Row.FRONT),
    BACK_ONE(Row.BACK),
    BACK_TWO(Row.BACK),
    BACK_THREE(Row.BACK);

    val backRow: Boolean = row == Row.BACK

    companion object {
        private val startingMagicCrystalLocations = arrayOf(
            FRONT_ONE, FRONT_TWO, FRONT_THREE, FRONT_FOUR, BACK_ONE, BACK_THREE
        )

        fun randomStartingMagicCrystalLocation() = startingMagicCrystalLocations.random()

        // TODO these can be a set
        val frontPositions = listOf(FRONT_ONE, FRONT_TWO, FRONT_THREE, FRONT_FOUR)
        val backPositions = listOf(BACK_ONE, BACK_TWO, BACK_THREE)
        val allPositions = values().toSet()

        // TODO: make this return null and throw the error elsewhere
        fun stringToPosition(s: String): Position {
            return when (s[0].toUpperCase()) {
                'B' -> {
                    when (s[1]) {
                        '1' -> BACK_ONE
                        '2' -> BACK_TWO
                        '3' -> BACK_THREE
                        else -> throw ActionInputException("invalid position")
                    }
                }
                'F' -> {
                    when (s[1]) {
                        '1' -> FRONT_ONE
                        '2' -> FRONT_TWO
                        '3' -> FRONT_THREE
                        '4' -> FRONT_FOUR
                        else -> throw ActionInputException("invalid position")
                    }
                }
                else -> throw ActionInputException("position not valid")
            }
        }
    }
}

enum class ActivationState(
    val canMove: Boolean,
    val canAct: Boolean
) {
    NOT_READY(false, false),
    READY(true, true),
    MOVED(false, true),
    READY_AGAIN(true, true),
    ACTIVATED(false, false);
}

@Serializable
data class Master(
    override val id: String,
    override val card: GameMasterCard,
    override var position: Position,
    override var activationState: ActivationState,
    override var attack: Int,
    override var hp: Int,
    override var maxHp: Int,
    override val range: Range,
    override var guardCount: Int = 0,
    override var sealCount: Int = 0
) : Creature() {
    override val element: Element? = null
    override val speed = Speed.NORMAL
}

@Serializable
data class Natial(
    override val id: String,
    override val card: GameNatialCard,
    override var position: Position,
    override var activationState: ActivationState,
    override var attack: Int,
    override var hp: Int,
    override var maxHp: Int,
    override val range: Range,
    override val speed: Speed,
    override val element: Element,
    override var guardCount: Int = 0,
    override var sealCount: Int = 0
) : Creature()

object Natials {
    fun summonFromCardToPosition(gameCard: GameNatialCard, position: Position, magicCrystal: Boolean): Natial {
        val card = Cards.getNatialByName(gameCard.cardName)
            ?: error("I don't know what this Natial is ${gameCard.cardName}")
        return Natial(
            id = UUID.randomUUID().toString(),
            card = gameCard,
            position = position,
            activationState = when (card.speed) {
                Speed.NORMAL -> ActivationState.NOT_READY
                Speed.FAST -> ActivationState.READY
            },
            attack = card.attack.let { if (magicCrystal) it + 1 else it },
            hp = card.hp.let { if (magicCrystal) it + 1 else it },
            maxHp = card.hp.let { if (magicCrystal) it + 1 else it },
            range = card.range,
            speed = card.speed,
            element = card.element
        )
    }
}

object Masters {
    fun masterFromCardToPosition(gameCard: GameMasterCard, position: Position): Master {
        val card = Cards.getMasterByName(gameCard.cardName)
            ?: error("I don't know who this Master is ${gameCard.cardName}")
        return Master(
            id = UUID.randomUUID().toString(),
            card = gameCard,
            position = position,
            activationState = ActivationState.NOT_READY,
            attack = card.attack,
            hp = card.hp,
            maxHp = card.hp,
            range = card.range
        )
    }
}

