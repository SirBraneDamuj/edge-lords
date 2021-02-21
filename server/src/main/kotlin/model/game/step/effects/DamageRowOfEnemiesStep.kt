package model.game.step.effects

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.Row
import model.game.step.GameStep

class DamageRowOfEnemiesStep(
    val targetPlayerLabel: PlayerLabel,
    val row: Row,
    val dealerPosition: Position,
    val damage: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val targetPlayer = game.player(targetPlayerLabel)
        return targetPlayer
            .creatures
            .filter { it.position.row == row }
            .map {
                InterCreatureDamageStep(
                    dealerPlayerLabel = targetPlayerLabel.other,
                    dealerPosition = dealerPosition,
                    receiverPosition = it.position,
                    damageAmount = damage
                )
            }
    }
}
