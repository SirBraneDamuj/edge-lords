package model.game.action

import model.game.*
import model.game.step.core.SummonNatialStep

class SummonAction(
    override val playerLabel: PlayerLabel,
    private val handIndex: Int,
    private val position: Position
) : Action {
    override fun validate(game: Game): ActionResult {
        val player = game.players.getValue(playerLabel)
        val card = player.hand.getOrNull(handIndex)
            ?: return invalidAction("Don't know what card $handIndex is")
        if (card !is GameNatialCard) {
            return invalidAction("Card at $handIndex is not a Natial")
        }
        if (player.mana < card.manaCost) {
            return invalidAction("Not enough mana")
        }
        if (player.creatureAtPosition(position) != null) {
            return invalidAction("There is already a Natial at $position")
        }
        return ValidAction(
            SummonNatialStep(playerLabel, handIndex, position)
        )
    }
}
