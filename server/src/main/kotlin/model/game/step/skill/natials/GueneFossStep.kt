package model.game.step.skill.natials

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.DealDamageStep
import util.toSingletonList

const val GUENEFOSS_DAMAGE = 2

class GueneFossStep(
    private val myPosition: Position,
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DealDamageStep(
            game.activePlayerLabel,
            myPosition,
            position,
            GUENEFOSS_DAMAGE
        ).toSingletonList()
    }
}
