package model.game.step.skill.natials

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.InterCreatureDamageStep
import util.toSingletonList

const val NEPTJUNO_DAMAGE = 2

class NeptjunoStep(
    private val myPosition: Position,
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return InterCreatureDamageStep(
            dealerPlayerLabel = game.activePlayerLabel,
            dealerPosition = myPosition,
            receiverPosition = position,
            damageAmount = NEPTJUNO_DAMAGE
        ).toSingletonList()
    }
}
