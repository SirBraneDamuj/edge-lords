package model.game

import kotlinx.serialization.Serializable

interface Creature {
    val card: GameCard
    val position: Position
    val activationState: ActivationState
    val attack: Int
    val hp: Int
    val maxHp: Int
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
    override val position: Position,
    override val activationState: ActivationState,
    override val attack: Int,
    override val hp: Int,
    override val maxHp: Int
) : Creature

@Serializable
data class Natial(
    override val card: GameNatialCard,
    override val position: Position,
    override val activationState: ActivationState,
    override val attack: Int,
    override val hp: Int,
    override val maxHp: Int,
) : Creature
