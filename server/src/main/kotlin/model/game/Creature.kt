package model.game

import client.ActionInputException
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeInfo
import model.Cards
import model.DOUBLE_ACTORS
import model.Element
import model.Range
import model.Speed
import java.util.*
import kotlin.math.max

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
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
    abstract var neighborGuardCount: Int
    abstract var canUseSkill: Boolean?

    val sealed: Boolean
        @JsonIgnore get() = sealCount > 0
    val guarded: Boolean
        @JsonIgnore get() = guardCount > 0

    fun increaseMaxHp(amount: Int) {
        if (amount <= 0) throw IllegalArgumentException()
        maxHp += amount
        heal(amount)
    }

    fun decreaseMaxHp(amount: Int) {
        if (amount >= 0) throw IllegalArgumentException()
        maxHp = max(1, maxHp - amount)
        if (hp > maxHp) hp = maxHp
    }

    fun changeAttack(amount: Int) {
        attack = max(0, attack + amount)
    }

    fun heal(amount: Int) {
        hp = (hp + amount).coerceAtMost(maxHp)
    }

    fun receiveDamage(damage: Int) {
        if (neighborGuardCount > 0) return
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

    companion object {
        @JsonCreator
        @JvmStatic
        private fun creator(name: String): Creature? {
            return Creature::class
                .sealedSubclasses
                .firstOrNull {
                    it.simpleName == name
                }
                ?.objectInstance
        }
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
    val neighbors: Set<Position>
        get() {
            return when (this) {
                FRONT_ONE -> setOf(FRONT_TWO)
                FRONT_TWO -> setOf(FRONT_ONE, FRONT_THREE)
                FRONT_THREE -> setOf(FRONT_TWO, FRONT_FOUR)
                FRONT_FOUR -> setOf(FRONT_THREE)
                BACK_ONE -> setOf(BACK_TWO)
                BACK_TWO -> setOf(BACK_ONE, BACK_THREE)
                BACK_THREE -> setOf(BACK_TWO)
            }
        }

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
        // TODO: NO NO NO NO NO NO NO
        fun stringToPosition(s: String): Position {
            try {
                return valueOf(s)
            } catch (e: IllegalArgumentException) {
            }
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
    MOVED_AGAIN(false, true),
    READY_AGAIN(true, true),
    ACTIVATED(false, false);

    fun stateAfterActing(creatureName: String) = when (this) {
        READY, MOVED -> {
            if (creatureName in DOUBLE_ACTORS) {
                READY_AGAIN
            } else {
                ACTIVATED
            }
        }
        else -> ACTIVATED
    }

    fun stateAfterMoving() = when (this) {
        READY_AGAIN -> MOVED_AGAIN
        else -> MOVED
    }
}

data class Master(
    override val id: String,
    override val card: GameMasterCard,
    override var position: Position,
    override var activationState: ActivationState,
    override var attack: Int,
    override var hp: Int,
    override var maxHp: Int,
    override val range: Range,
    override var canUseSkill: Boolean?,
    override var guardCount: Int = 0,
    override var sealCount: Int = 0,
    override var neighborGuardCount: Int = 0
) : Creature() {
    override val element: Element? = null
    override val speed = Speed.NORMAL
}

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
    override var canUseSkill: Boolean?,
    override var guardCount: Int = 0,
    override var sealCount: Int = 0,
    override var neighborGuardCount: Int = 0
) : Creature()

object Natials {
    fun summonFromCardToPosition(gameCard: GameNatialCard, position: Position): Natial {
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
            attack = card.attack,
            hp = card.hp,
            maxHp = card.hp,
            range = card.range,
            speed = card.speed,
            element = card.element,
            canUseSkill = if (card.targetingMode == EffectTargetingMode.NONE) null else true
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
            range = card.range,
            canUseSkill = if (card.targetingMode == EffectTargetingMode.NONE) null else true
        )
    }
}
