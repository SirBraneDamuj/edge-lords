package model.game.step.skill.natials

import model.game.Game
import model.game.Row
import model.game.step.GameStep
import model.game.step.effects.DamageRowOfEnemiesStep
import util.toSingletonList

const val DULLMDALLA_DAMAGE = 1

class DullmdallaStep(
    private val row: Row
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageRowOfEnemiesStep(
            targetPlayerLabel = game.inactivePlayer.playerLabel,
            row = row,
            damage = DULLMDALLA_DAMAGE
        ).toSingletonList()
    }
}
