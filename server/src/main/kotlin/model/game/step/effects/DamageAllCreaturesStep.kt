package model.game.step.effects

import model.game.Game
import model.game.PlayerLabel
import model.game.step.GameStep
import model.game.step.core.DetectDeadCreaturesStep
import util.toSingletonList

class DamageAllCreaturesStep(
    private val playerLabel: PlayerLabel,
    private val damage: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        player.creatures.forEach {
            it.receiveDamage(damage)
        }
        return DetectDeadCreaturesStep().toSingletonList()
    }
}
