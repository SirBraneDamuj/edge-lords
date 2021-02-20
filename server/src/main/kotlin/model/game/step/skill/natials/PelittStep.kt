package model.game.step.skill.natials

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.CureSealStep
import util.toSingletonList

class PelittStep(
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return CureSealStep(
            playerLabel = game.activePlayerLabel,
            position = position
        ).toSingletonList()
    }
}
