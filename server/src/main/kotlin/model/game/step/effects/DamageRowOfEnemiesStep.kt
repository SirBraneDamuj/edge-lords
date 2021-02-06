package model.game.step.effects

import model.game.Game
import model.game.PlayerLabel
import model.game.Row
import model.game.step.GameStep
import model.game.step.core.DetectDeadCreaturesStep
import util.toSingletonList

class DamageRowOfEnemiesStep(
    val targetPlayerLabel: PlayerLabel,
    val row: Row,
    val damage: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val targetPlayer = game.player(targetPlayerLabel)
        targetPlayer
            .creatures
            .filter { it.position.row == row }
            .forEach {
                it.hp -= damage
            }
        return DetectDeadCreaturesStep().toSingletonList()
    }
}
