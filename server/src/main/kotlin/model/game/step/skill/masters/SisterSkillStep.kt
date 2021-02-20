package model.game.step.skill.masters

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.IncreaseCreatureAttackStep
import util.toSingletonList

const val SISTER_ATTACK_INCREASE = 2

class SisterSkillStep(
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return IncreaseCreatureAttackStep(
            game.activePlayerLabel,
            position,
            SISTER_ATTACK_INCREASE
        ).toSingletonList()
    }
}