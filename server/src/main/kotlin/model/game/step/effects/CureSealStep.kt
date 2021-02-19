package model.game.step.effects

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

class CureSealStep(
    private val playerLabel: PlayerLabel,
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(position)
            ?: error("no creature found... was this action validated?")
        creature.sealCount = 0
        return emptyList()
    }
}
