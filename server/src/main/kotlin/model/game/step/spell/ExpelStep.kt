package model.game.step.spell

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.core.CreatureChangePositionStep
import model.game.step.core.RemoveCreatureStep
import model.game.step.effects.ReturnCardToHand

class ExpelStep(
    val playerLabel: PlayerLabel,
    val targetPosition: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val targetPlayer = game.player(playerLabel.other)
        val creature = targetPlayer.creatureAtPosition(targetPosition)
            ?: error("There is no creature there... was this action validated?")
        return listOf(
            CreatureChangePositionStep(
                playerLabel = playerLabel,
                fromPosition = targetPosition,
                toPosition = null
            ),
            ReturnCardToHand(targetPlayer.playerLabel, creature.card),
            RemoveCreatureStep(targetPlayer.playerLabel, targetPosition)
        )
    }
}
