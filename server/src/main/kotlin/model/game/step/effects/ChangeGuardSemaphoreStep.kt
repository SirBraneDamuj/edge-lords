package model.game.step.effects

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

class ChangeGuardSemaphoreStep(
    private val playerLabel: PlayerLabel,
    private val position: Position,
    private val changeAmount: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(position) ?: error("boo boo boo")
        creature.neighborGuardCount += changeAmount
        return emptyList()
    }
}
