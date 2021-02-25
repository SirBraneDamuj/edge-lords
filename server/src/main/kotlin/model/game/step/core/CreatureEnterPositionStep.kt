package model.game.step.core

import model.Element
import model.game.*
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
        return when (creature.card.cardName) {
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
                val previousNeighborSteps = fromPosition!!.neighbors.mapNotNull { neighbor ->
                    if (player.creatureAtPosition(neighbor) == null) null
                    else ChangeStatsStep(
                        playerLabel = playerLabel,
                        position = neighbor,
                        attackChange = -2,
                        hpChange = -2
                    )
                }
                val newNeighborSteps = position.neighbors.mapNotNull { neighbor ->
                    if (player.creatureAtPosition(neighbor) == null) null
                    else ChangeStatsStep(
                        playerLabel = playerLabel,
                        position = neighbor,
                        attackChange = 2,
                        hpChange = 2
                    )
                }
                previousNeighborSteps + newNeighborSteps
            }
            "Neptjuno", "Pa-Rancell" -> {
                val previousNeighborSteps = fromPosition?.neighbors?.mapNotNull { neighbor ->
                    player.creatureAtPosition(neighbor)?.let {
                        ChangeGuardSemaphoreStep(
                            playerLabel = playerLabel,
                            position = neighbor,
                            changeAmount = -1
                        )
                    }
                } ?: emptyList()
                val currentNeighborSteps = position.neighbors.mapNotNull { neighbor ->
                    player.creatureAtPosition(neighbor)?.let {
                        ChangeGuardSemaphoreStep(
                            playerLabel = playerLabel,
                            position = neighbor,
                            changeAmount = 1
                        )
                    }
                }
                previousNeighborSteps + currentNeighborSteps
            }
            "Regna-Croxe" -> {
                if (fromPosition != null) emptyList()
                else {
                    player.creatures.mapNotNull { ally ->
                        ally.takeIf { it.element == Element.HEAVEN }
                            ?.let { heavenAlly ->
                                ChangeStatsStep(
                                    playerLabel = playerLabel,
                                    position = heavenAlly.position,
                                    attackChange = 1,
                                    hpChange = 0
                                )
                            }
                    }
                }
            }
            else -> {
                val previousNeighbors = fromPosition?.neighbors?.mapNotNull(player::creatureAtPosition) ?: emptyList()
                val neighbors = creature.position.neighbors.mapNotNull(player::creatureAtPosition)
                val previousNeighborSteps = previousNeighbors.mapNotNull { neighborBuffs(it, -1) }
                val neighborSteps = neighbors.mapNotNull { neighborBuffs(it, 1) }
                val regnaCroxeSteps = if (fromPosition != null) {
                    emptyList()
                } else {
                    val regnaCroxes = player.creatures.filter { it.card.cardName == "Regna-Croxe" }
                    regnaCroxes.map {
                        ChangeStatsStep(
                            playerLabel = playerLabel,
                            position = position,
                            attackChange = 1,
                            hpChange = 0
                        )
                    }
                }
                val previousDaColms = fromPosition?.row?.positions()
                    ?.mapNotNull(player::creatureAtPosition)
                    ?.filter { it.card.cardName == "Da-Colm" }
                    ?.map {
                        ChangeStatsStep(
                            playerLabel = playerLabel,
                            position = position,
                            attackChange = 0,
                            hpChange = -1
                        )
                    }
                    ?: emptyList()
                val newDaColms = position.row.positions()
                    .mapNotNull(player::creatureAtPosition)
                    .filter { it.card.cardName == "Da-Colm" }
                    .map {
                        ChangeStatsStep(
                            playerLabel = playerLabel,
                            position = position,
                            attackChange = 0,
                            hpChange = 1
                        )
                    }
                previousNeighborSteps + neighborSteps + regnaCroxeSteps + previousDaColms + newDaColms
            }
        }
    }

    private fun neighborBuffs(neighbor: Creature, direction: Int): GameStep? {
        return when (neighbor.card.cardName) {
            "Tyrant" -> ChangeStatsStep(
                playerLabel = playerLabel,
                position = position,
                attackChange = 2 * direction,
                hpChange = 2 * direction
            )
            "Neptjuno", "Pa-Rancell" -> ChangeGuardSemaphoreStep(
                playerLabel = playerLabel,
                position = position,
                changeAmount = 1 * direction
            )
            else -> null
        }
    }
}
