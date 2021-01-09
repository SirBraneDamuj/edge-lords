package model.game.step.spell

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.ReturnCardToDeck
import util.toSingletonList

class ExpelStep(
    val playerLabel: PlayerLabel,
    val targetPosition: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val targetPlayer = game.player(playerLabel.other)
        val creature = targetPlayer.creatureAtPosition(targetPosition)
            ?: error("There is no creature there... was this action validated?")
        targetPlayer.creatures = targetPlayer.creatures - creature
        return ReturnCardToDeck(targetPlayer.playerLabel, creature.card).toSingletonList()
    }
}
