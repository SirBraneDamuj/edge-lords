package model.game.step.core

import model.CARD_DRAWERS
import model.Cards
import model.Element
import model.game.Game
import model.game.Master
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.ChangeGuardSemaphoreStep
import model.game.step.effects.ChangeStatsStep
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
            player.discard = player.discard + creature.card
            // TODO: abilities which trigger on natial death should happen here
            mutableListOf<GameStep>().apply {
                if (creature.card.cardName in CARD_DRAWERS) {
                    this.add(DrawCardStep(playerLabel))
                }
                this.add(CreatureChangePositionStep(
                    playerLabel = playerLabel,
                    fromPosition = position,
                    toPosition = null
                ))
                this.add(RemoveCreatureStep(playerLabel, position))
            }
        }
    }
}
