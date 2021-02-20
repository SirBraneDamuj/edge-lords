package model.game.step.skill.natials

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.HealCreatureStep
import util.toSingletonList

const val MARME_HP_RESTORATION = 3

class MarmeStep(
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return HealCreatureStep(
            game.activePlayerLabel,
            position,
            MARME_HP_RESTORATION
        ).toSingletonList()
    }
}
