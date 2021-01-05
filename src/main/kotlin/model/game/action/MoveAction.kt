package model.game.action

import model.game.Game
import model.game.PlayerLabel
import model.game.Position

class MoveAction(
    override val playerLabel: PlayerLabel,
    private val from: Position,
    private val to: Position
) : Action {
    override fun perform(game: Game): ActionResult {
        val player = game.players.getValue(playerLabel)
        val creature = player.creatureAtPosition(from) ?: return invalidAction("There is no creature at $from")
        val destinationCreature = player.creatureAtPosition(to)
        creature.position = to
        destinationCreature?.position = from
        return ValidAction(game)
    }
}
