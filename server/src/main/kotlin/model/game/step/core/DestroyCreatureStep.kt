package model.game.step.core

import model.CARD_DRAWERS
import model.game.Game
import model.game.Master
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import util.toSingletonList

class DestroyCreatureStep(
    val playerLabel: PlayerLabel,
    val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(position) ?: error("creature wasn't found. was the action validated?")
        return if (creature is Master) {
            listOf(VictoryStep(playerLabel.other))
        } else {
            player.creatures = player.creatures - creature
            player.discard = player.discard + creature.card
            // TODO: abilities which trigger on natial death should happen here
            if (creature.card.cardName in CARD_DRAWERS) {
                return DrawCardStep(playerLabel).toSingletonList()
            }
            return emptyList()
        }
    }
}
