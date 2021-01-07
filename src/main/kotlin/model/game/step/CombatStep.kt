package model.game.step

import model.DEFENDER_STRENGTH_PENALTY
import model.game.Game
import model.game.PlayerLabel
import model.game.Position

class CombatStep(
    private val attackingPlayerLabel: PlayerLabel,
    private val attackerPosition: Position,
    private val defenderPosition: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val attackingPlayer = game.player(attackingPlayerLabel)
        val defendingPlayer = game.player(attackingPlayerLabel.other)
        val attacker = attackingPlayer.creatureAtPosition(attackerPosition)
            ?: error("no creature found at attacker position... was the action validated?")
        val defender = defendingPlayer.creatureAtPosition(defenderPosition)
            ?: error("no creature found at defender position... was the action validated?")

        var attackerStrength = attacker.attack
        attackerStrength += attacker.element?.strengthModifierAgainst(defender.element) ?: 0
        var defenderStrength = defender.attack - DEFENDER_STRENGTH_PENALTY
        defenderStrength += defender.element?.strengthModifierAgainst(attacker.element) ?: 0

        attacker.receiveDamage(defenderStrength)
        defender.receiveDamage(attackerStrength)

        // TODO: abilities which trigger when one Natial kills another should trigger here
        return mutableListOf<GameStep>().apply {
            if (attacker.hp <= 0) add(DestroyCreatureStep(attackingPlayer.playerLabel, attackerPosition))
            if (defender.hp <= 0) add(DestroyCreatureStep(defendingPlayer.playerLabel, defenderPosition))
        }
    }
}
