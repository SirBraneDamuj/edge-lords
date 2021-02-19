package model.game.step.effects

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

class SealCreatureStep(
    private val targetPlayerLabel: PlayerLabel,
    private val targetPosition: Position,
    private val sealAmount: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val targetPlayer = game.player(targetPlayerLabel)
        val targetCreature = targetPlayer.creatureAtPosition(targetPosition)
            ?: error("No creature found... was this action validated?")
        targetCreature.sealCount += sealAmount
        return emptyList()
    }
}
