package model.game.step.effects

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

class ChangeStatsStep(
    private val playerLabel: PlayerLabel,
    private val position: Position,
    private val attackChange: Int,
    private val hpChange: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(position) ?: return emptyList()
        creature.changeAttack(attackChange)
        if (hpChange > 0) creature.increaseMaxHp(hpChange)
        if (hpChange < 0) creature.decreaseMaxHp(hpChange)
        return emptyList()
    }
}
