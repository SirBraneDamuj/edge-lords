package model.game.step.spell

import model.game.Game
import model.game.PlayerLabel
import model.game.Row
import model.game.step.effects.DamageRowOfEnemiesStep
import model.game.step.GameStep
import model.game.step.core.DetectDeadCreaturesStep
import util.toSingletonList

const val DISASTER_DAMAGE = 4

class DisasterStep(
    val playerLabel: PlayerLabel,
    val row: Row
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val targetPlayer = game.player(playerLabel.other)
        targetPlayer.creatures.forEach {
            if (it.position.row == row) {
                it.receiveDamage(DISASTER_DAMAGE)
            }
        }
        return DetectDeadCreaturesStep().toSingletonList()
    }
}
