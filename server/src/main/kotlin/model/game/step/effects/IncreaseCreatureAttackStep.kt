package model.game.step.effects

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

class IncreaseCreatureAttackStep(
    private val playerLabel: PlayerLabel,
    private val position: Position,
    private val amount: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(position)
            ?: error("No creature found... was this action validated?")
        creature.attack += amount
        return emptyList()
    }
}