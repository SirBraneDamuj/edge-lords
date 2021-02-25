package model.game.step.core

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.spell.MagicCrystalStep
import util.toSingletonList

class MoveCreatureStep(
    private val playerLabel: PlayerLabel,
    private val from: Position,
    private val to: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(from) ?: error("There is no creature at $from")
        val destinationCreature = player.creatureAtPosition(to)
        creature.position = to
        creature.activationState = creature.activationState.stateAfterMoving()
        destinationCreature?.position = from
        return mutableListOf<GameStep>().apply {
            if (player.magicCrystals.contains(to)) {
                this.add(MagicCrystalStep(playerLabel, to))
            }
            if (destinationCreature != null) {
                // the swapped creature is moving from "to" to "from" :S
                this.add(CreatureEnterPositionStep(
                    playerLabel = playerLabel,
                    fromPosition = to,
                    position = from
                ))
            }
            this.add(CreatureEnterPositionStep(
                playerLabel = playerLabel,
                fromPosition = from,
                position = to
            ))
        }
    }
}
