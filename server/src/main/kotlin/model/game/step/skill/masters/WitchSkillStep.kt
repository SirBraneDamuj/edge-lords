package model.game.step.skill.masters

import model.game.Game
import model.game.Master
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.InterCreatureDamageStep
import util.toSingletonList

const val WITCH_DAMAGE_AMOUNT = 4

class WitchSkillStep(
    private val myPosition: Position,
    private val targetPosition: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return InterCreatureDamageStep(
            dealerPlayerLabel = game.activePlayerLabel,
            dealerPosition = myPosition,
            receiverPosition = targetPosition,
            damageAmount = WITCH_DAMAGE_AMOUNT
        ).toSingletonList()
    }
}