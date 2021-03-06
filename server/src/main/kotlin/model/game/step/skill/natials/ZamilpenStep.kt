package model.game.step.skill.natials

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.SealCreatureStep
import util.toSingletonList

const val ZAMILPEN_SEAL_COUNT = 1

class ZamilpenStep(
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return SealCreatureStep(
            game.inactivePlayer.playerLabel,
            position,
            ZAMILPEN_SEAL_COUNT
        ).toSingletonList()
    }
}
