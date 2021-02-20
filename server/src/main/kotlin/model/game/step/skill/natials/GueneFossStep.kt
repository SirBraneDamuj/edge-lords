package model.game.step.skill.natials

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.DamageSingleCreatureStep
import util.toSingletonList

const val GUENEFOSS_DAMAGE = 2

class GueneFossStep(
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageSingleCreatureStep(
            targetPlayerLabel = game.inactivePlayer.playerLabel,
            position = position,
            damage = GUENEFOSS_DAMAGE
        ).toSingletonList()
    }
}
