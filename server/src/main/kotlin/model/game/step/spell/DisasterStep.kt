package model.game.step.spell

import model.game.Game
import model.game.PlayerLabel
import model.game.Row
import model.game.step.GameStep
import model.game.step.effects.DealDamageStep

const val DISASTER_DAMAGE = 4

class DisasterStep(
    val playerLabel: PlayerLabel,
    val row: Row
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val targetPlayer = game.player(playerLabel.other)
        val damageSteps = mutableListOf<GameStep>()
        targetPlayer.creatures.forEach {
            if (it.position.row == row) {
                damageSteps.add(DealDamageStep(
                    dealerPlayerLabel = game.activePlayerLabel,
                    dealerPosition = null,
                    receiverPosition = it.position,
                    damageAmount = DISASTER_DAMAGE
                ))
            }
        }
        return damageSteps
    }
}
