package model.game.step.skill

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.DamageSingleCreatureStep
import util.toSingletonList

const val NEPTJUNO_DAMAGE = 2

class NeptjunoStep(
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageSingleCreatureStep(
            game.inactivePlayer.playerLabel,
            position,
            NEPTJUNO_DAMAGE
        ).toSingletonList()
    }
}
