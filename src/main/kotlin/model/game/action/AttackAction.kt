package model.game.action

import model.game.*

class AttackAction(
    private val playerLabel: PlayerLabel,
    private val attackerPosition: Position,
    private val defenderPosition: Position
) : Action() {
    override fun perform(game: Game): ActionResult {
        val player = game.players.getValue(playerLabel)
        val otherPlayer = game.players.getValue(playerLabel.other)

        val attacker = player.creatureAtPosition(attackerPosition)
            ?: return invalidAction("$playerLabel does not have a creature at $attackerPosition")
        if (attacker.sealed) return invalidAction("${attacker.card.card.name} is sealed.")

        val defender = otherPlayer.creatureAtPosition(defenderPosition)
            ?: return invalidAction("${playerLabel.other} does not have a creature at $defenderPosition")

        var attackerStrength = attacker.attack
        attackerStrength += attacker.element?.strengthModifierAgainst(defender.element) ?: 0
        var defenderStrength = defender.attack - 1
        defenderStrength += defender.element?.strengthModifierAgainst(attacker.element) ?: 0

        attacker.receiveDamage(defenderStrength)
        defender.receiveDamage(attackerStrength)

        return ValidAction(game)
    }
}
