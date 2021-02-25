package model.game.step.core

import model.CARD_DRAWERS
import model.Cards
import model.game.Game
import model.game.Master
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.ChangeGuardSemaphoreStep
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
            val drawCardStep = if (creature.card.cardName in CARD_DRAWERS) {
                DrawCardStep(playerLabel).toSingletonList()
            } else emptyList()
            val guardsNeighbors = Cards.getNatialByName(creature.card.cardName)?.guardsNeighbors ?: false
            val guardSemaphoreSteps = if (guardsNeighbors) {
                position.neighbors.mapNotNull { neighbor ->
                    val neighborCreature = player.creatureAtPosition(neighbor)
                    neighborCreature?.let {
                        ChangeGuardSemaphoreStep(playerLabel, neighbor, -1)
                    }
                }
            } else emptyList()
            drawCardStep + guardSemaphoreSteps
        }
    }
}
