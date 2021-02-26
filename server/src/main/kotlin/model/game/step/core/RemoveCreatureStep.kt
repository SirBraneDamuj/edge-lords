package model.game.step.core

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

class RemoveCreatureStep(
    private val playerLabel: PlayerLabel,
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(position)
            ?: error("tried to remove a creature that was already gone...")
        player.creatures = player.creatures - creature
        return emptyList()
    }
}
