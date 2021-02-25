package model.game.step.skill.natials

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.DealDamageStep
import util.toSingletonList

const val ZENOSBLEAD_DAMAGE = 5

class ZenosbleadStep(
    private val myPosition: Position,
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DealDamageStep(
            dealerPlayerLabel = game.activePlayerLabel,
            dealerPosition = myPosition,
            receiverPosition = position,
            damageAmount = ZENOSBLEAD_DAMAGE
        ).toSingletonList()
    }
}
