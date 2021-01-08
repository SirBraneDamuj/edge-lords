package model.game.action

import model.Range
import model.game.*
import model.game.step.CombatStep

class AttackAction(
    override val playerLabel: PlayerLabel,
    private val attackerPosition: Position,
    private val defenderPosition: Position
) : Action {
    override fun validate(game: Game): ActionResult {
        val player = game.players.getValue(playerLabel)
        val otherPlayer = game.players.getValue(playerLabel.other)

        val attacker = player.creatureAtPosition(attackerPosition)
            ?: return invalidAction("$playerLabel does not have a creature at $attackerPosition")

        if (attackerPosition.backRow && attacker.range != Range.RANGED) {
            return invalidAction("${attacker.card.cardName} cannot attack from the back row")
        }

        if (attacker.sealed) return invalidAction("${attacker.card.cardName} is sealed.")

        otherPlayer.creatureAtPosition(defenderPosition)
            ?: return invalidAction("${playerLabel.other} does not have a creature at $defenderPosition")

        return ValidAction(
            CombatStep(playerLabel, attackerPosition, defenderPosition)
        )
    }
}
