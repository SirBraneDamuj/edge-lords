package model.game.step.core

import model.Element
import model.game.*
import model.game.step.GameStep
import model.game.step.effects.ChangeGuardSemaphoreStep
import model.game.step.effects.ChangeStatsStep
import util.toSingletonList

class CreatureChangePositionStep(
    private val playerLabel: PlayerLabel,
    private val fromPosition: Position?, // if this is null, the creature is entering the field
    private val toPosition: Position? // if this is null, the creature is exiting the field
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = toPosition?.let(player::creatureAtPosition)
            ?: fromPosition?.let(player::creatureAtPosition)
            ?: error("When changing position, at least one of the two positions must not be null")
        return when (creature.card.cardName) {
            "Da-Colm" -> {
                // he is entering a new row
                if (fromPosition?.row != toPosition?.row) {
                    player.creatures.mapNotNull { other ->
                        if (other == creature) return@mapNotNull null
                        val hpChange = when (other.position.row) {
                            // creatures in the row he is leaving lose 1 HP
                            fromPosition?.row -> -1
                            // creatures in the row he is entering gain 1 HP
                            toPosition?.row -> 1
                            else -> 0
                        }
                        if (hpChange != 0) {
                            ChangeStatsStep(
                                playerLabel = playerLabel,
                                position = other.position,
                                attackChange = 0,
                                hpChange = hpChange
                            )
                        } else null
                    }
                } else emptyList()
            }
            "Amoltamiss" -> {
                // if toPosition is null, that means he's leaving the field
                toPosition?.let {
                    val attackChange = when (toPosition.row) {
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
                        position = toPosition,
                        attackChange = attackChange,
                        hpChange = 0
                    ).toSingletonList()
                } ?: emptyList()
            }
            "Tyrant" -> { // Tyrant is always present on the field so I use !!
                // previous neighbors lose the stats
                val previousNeighborSteps = fromPosition!!.neighbors.mapNotNull { neighbor ->
                    if (player.creatureAtPosition(neighbor) == null) null
                    else ChangeStatsStep(
                        playerLabel = playerLabel,
                        position = neighbor,
                        attackChange = -2,
                        hpChange = -2
                    )
                }
                // new neighbors gain the stats
                val newNeighborSteps = toPosition!!.neighbors.mapNotNull { neighbor ->
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
                // previous neighbors lose a guard count
                val previousNeighborSteps = fromPosition?.neighbors?.mapNotNull { neighbor ->
                    player.creatureAtPosition(neighbor)?.let {
                        ChangeGuardSemaphoreStep(
                            playerLabel = playerLabel,
                            position = neighbor,
                            changeAmount = -1
                        )
                    }
                } ?: emptyList()
                // new neighbors gain a guard count
                val currentNeighborSteps = toPosition?.neighbors?.mapNotNull { neighbor ->
                    player.creatureAtPosition(neighbor)?.let {
                        ChangeGuardSemaphoreStep(
                            playerLabel = playerLabel,
                            position = neighbor,
                            changeAmount = 1
                        )
                    }
                } ?: emptyList()
                previousNeighborSteps + currentNeighborSteps
            }
            "Regna-Croxe" -> {
                if (fromPosition != null) emptyList()
                else {
                    player.creatures.mapNotNull { ally ->
                        ally.takeIf { it != creature && it.element == Element.HEAVEN }
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
                // the creature is exiting. Once it gets down here, we know it has no effects to clean up and can safely exit the field
                if (toPosition == null) return emptyList()
                val previousNeighbors = fromPosition?.neighbors?.mapNotNull(player::creatureAtPosition) ?: emptyList()
                val newNeighbors = creature.position.neighbors.mapNotNull(player::creatureAtPosition)
                // if it was near any neighbor-affecting creatures, we need to remove their effects
                val previousNeighborSteps = previousNeighbors.mapNotNull { neighborBuffs(it, -1, toPosition) }
                // if it moves near any neighbor-affecting creatures, we need to add their effects
                val neighborSteps = newNeighbors.mapNotNull { neighborBuffs(it, 1, toPosition) }
                // if the creature is entering the field, we need to apply global effects
                val globalSteps = if (fromPosition != null) {
                    emptyList()
                } else {
                    val regnaCroxes = player.creatures.filter { it.card.cardName == "Regna-Croxe" && it != creature }
                    val spirit = player.creatures
                        .find { it.card.cardName == "Spirit" }
                        ?.takeIf { it.hp <= 10 }
                    mutableListOf<GameStep>().apply {
                        regnaCroxes.forEach {
                            this.add(ChangeStatsStep(
                                playerLabel = playerLabel,
                                position = toPosition,
                                attackChange = 1,
                                hpChange = 0
                            ))
                        }
                        spirit?.let {
                            this.add(ChangeStatsStep(
                                playerLabel = playerLabel,
                                position = toPosition,
                                attackChange = 1,
                                hpChange = 1
                            ))
                        }
                    }
                }
                val previousRowSteps = fromPosition?.row?.positions()
                    ?.mapNotNull(player::creatureAtPosition)
                    ?.filter { it.card.cardName == "Da-Colm" }
                    ?.map {
                        ChangeStatsStep(
                            playerLabel = playerLabel,
                            position = toPosition,
                            attackChange = 0,
                            hpChange = -1
                        )
                    }
                    ?: emptyList()
                val newRowSteps = toPosition.row.positions()
                    .mapNotNull(player::creatureAtPosition)
                    .filter { it.card.cardName == "Da-Colm" && it != creature }
                    .map {
                        ChangeStatsStep(
                            playerLabel = playerLabel,
                            position = toPosition,
                            attackChange = 0,
                            hpChange = 1
                        )
                    }
                previousNeighborSteps + neighborSteps + globalSteps + previousRowSteps + newRowSteps
            }
        }
    }

    private fun neighborBuffs(neighbor: Creature, direction: Int, position: Position): GameStep? {
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
