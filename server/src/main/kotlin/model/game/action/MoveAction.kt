package model.game.action

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.core.MoveCreatureStep

class MoveAction(
    override val playerLabel: PlayerLabel,
    private val from: Position,
    private val to: Position
) : Action {
    override fun validate(game: Game): ActionResult {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(from) ?: return invalidAction("There is no creature at $from")
        if (!creature.activationState.canMove || creature.sealed) {
            return invalidAction("The creature can't move right now.")
        }
        return ValidAction(
            MoveCreatureStep(playerLabel, from, to)
        )
    }
}
