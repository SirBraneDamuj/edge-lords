package model.game.step.skill.masters

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.DealDamageStep
import util.toSingletonList

const val WITCH_DAMAGE_AMOUNT = 4

class WitchSkillStep(
    private val myPosition: Position,
    private val targetPosition: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DealDamageStep(
            dealerPlayerLabel = game.activePlayerLabel,
            dealerPosition = myPosition,
            receiverPosition = targetPosition,
            damageAmount = WITCH_DAMAGE_AMOUNT
        ).toSingletonList()
    }
}
