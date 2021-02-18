package model.game.step.effects

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

class HealCreatureStep(
    private val playerLabel: PlayerLabel,
    private val position: Position,
    private val amount: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val targetCreature = player.creatureAtPosition(position)
            ?: error("no creature found... was this action validated?")
        targetCreature.heal(amount)
        return emptyList()
    }
}