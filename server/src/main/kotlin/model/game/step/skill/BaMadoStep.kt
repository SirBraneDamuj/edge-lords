package model.game.step.skill

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.SealCreatureStep
import util.toSingletonList

const val BAMADO_SEAL_COUNT = 2

class BaMadoStep(
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return SealCreatureStep(game.inactivePlayer.playerLabel, position, BAMADO_SEAL_COUNT).toSingletonList()
    }
}
