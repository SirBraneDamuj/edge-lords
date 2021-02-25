package model.game.step.core

import model.Cards
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.Row
import model.game.step.GameStep
import model.game.step.effects.ChangeGuardSemaphoreStep
import model.game.step.effects.ChangeStatsStep
import util.toSingletonList

class CreatureEnterPositionStep(
    private val playerLabel: PlayerLabel,
    private val fromPosition: Position?,
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(position)
            ?: error("you know the deal by now. where's this dang ol creature")
        val creatureSpecificSteps =  when (creature.card.cardName) {
            "Da-Colm" -> {
                if (fromPosition?.row != position.row) {
                    player.creatures.map { other ->
                        val hpChange = when (other.position.row) {
                            fromPosition?.row -> -1
                            position.row -> 1
                            else -> 0
                        }
                        ChangeStatsStep(
                            playerLabel = playerLabel,
                            position = other.position,
                            attackChange = 0,
                            hpChange = hpChange
                        )
                    }
                } else emptyList()
            }
            "Amoltamiss" -> {
                val attackChange = when (position.row) {
                    Row.FRONT -> {
                        when (fromPosition?.row) {
                            Row.BACK, null -> 2
                            Row.FRONT -> 0
                        }
                    }
                    Row.BACK -> {
                        when (fromPosition?.row) {
                            Row.BACK, null -> 0
                            Row.FRONT -> -2
                        }
                    }
                }
                ChangeStatsStep(
                    playerLabel = playerLabel,
                    position = position,
                    attackChange = attackChange,
                    hpChange = 0
                ).toSingletonList()
            }
            "Tyrant" -> {
                val previousNeighborSteps = fromPosition!!.neighbors.map { neighbor ->
                    ChangeStatsStep(
                        playerLabel = playerLabel,
                        position = neighbor,
                        attackChange = -2,
                        hpChange = -2
                    )
                }
                val newNeighborSteps = position.neighbors.map { neighbor ->
                    ChangeStatsStep(
                        playerLabel = playerLabel,
                        position = neighbor,
                        attackChange = 2,
                        hpChange = 2
                    )
                }
                previousNeighborSteps + newNeighborSteps
            }
            else -> {
                val neighbors = creature.position.neighbors.mapNotNull(player::creatureAtPosition)
                val neighborSteps = neighbors.map {
                    when (it.card.cardName) {
                        "Tyrant" ->
                    }
                }
                emptyList()
            }
        }
        val guardsNeighbors = Cards.getNatialByName(creature.card.cardName)?.guardsNeighbors ?: false
        val neighborGuardSteps = if (guardsNeighbors) {
            val fromCreatures = fromPosition?.neighbors?.mapNotNull { neighborPosition ->
                val neighbor = player.creatureAtPosition(neighborPosition)
                neighbor?.let {
                    ChangeGuardSemaphoreStep(playerLabel, neighborPosition, -1)
                }
            } ?: emptyList()
            val toCreatures = position.neighbors.mapNotNull { neighborPosition ->
                val neighbor = player.creatureAtPosition(neighborPosition)
                neighbor?.let {
                    ChangeGuardSemaphoreStep(playerLabel, neighborPosition, -1)
                }
            }
            fromCreatures + toCreatures
        } else {
            emptyList()
        }
        return creatureSpecificSteps + neighborGuardSteps
    }
}
