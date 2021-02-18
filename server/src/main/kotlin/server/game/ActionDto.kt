package server.game

import model.game.Position
import model.game.Row

data class ActionDto(
    val mulligan: MulliganDto? = null,
    val move: MoveDto? = null,
    val summon: SummonDto? = null,
    val attack: AttackDto? = null,
    val spell: SpellDto? = null,
    val skill: SkillDto? = null,
    val end: Boolean? = null
)

data class MulliganDto(
    val handIndices: List<Int>
)

data class MoveDto(
    val from: Position,
    val to: Position
)

data class SummonDto(
    val handIndex: Int,
    val position: Position
)

data class AttackDto(
    val attackPosition: Position,
    val targetPosition: Position
)

data class SpellDto(
    val handIndex: Int,
    val targetTokens: List<String>
)

data class SkillDto(
    val position: Position,
    val targetTokens: List<String>
)
