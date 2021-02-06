package model.game.step.core

import model.game.ActivationState
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
        creature.activationState = ActivationState.MOVED
        destinationCreature?.position = from
        return if (player.magicCrystals.contains(to)) {
            MagicCrystalStep(
                playerLabel = playerLabel,
                position = to
            ).toSingletonList()
        } else {
            return emptyList()
        }
    }
}
