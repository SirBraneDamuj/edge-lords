package model.game.step.spell

import model.game.Game
import model.game.PlayerLabel
import model.game.Row
import model.game.step.effects.DamageRowOfEnemiesStep
import model.game.step.GameStep
import util.toSingletonList

const val DISASTER_DAMAGE = 4

class DisasterStep(
    val playerLabel: PlayerLabel,
    val row: Row
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageRowOfEnemiesStep(playerLabel.other, row, DISASTER_DAMAGE)
            .toSingletonList()
    }
}
