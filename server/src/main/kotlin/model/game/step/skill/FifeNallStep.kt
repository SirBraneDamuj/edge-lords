package model.game.step.skill

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.HealCreatureStep
import util.toSingletonList

const val FIFENALL_HEAL = 6

class FifeNallStep(
    private val position: Position
) : GameStep{
    override fun perform(game: Game): List<GameStep> {
        return HealCreatureStep(
            game.activePlayerLabel,
            position,
            FIFENALL_HEAL
        ).toSingletonList()
    }
}
