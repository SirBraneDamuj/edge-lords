package model.game.step.core

import model.game.*
import model.game.step.GameStep

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

        val (attackerStrength, defenderStrength) = DamageCalculator.calculateCombatDamage(attacker, defender)

        // TODO: this might be more complicated than this
        if (!attacker.position.backRow) {
            attacker.receiveDamage(defenderStrength)
        }
        defender.receiveDamage(attackerStrength)

        attacker.activationState = ActivationState.ACTIVATED

        // TODO: abilities which trigger when one Natial kills another should trigger here
        return mutableListOf<GameStep>().apply {
            if (attacker.hp <= 0) add(DestroyCreatureStep(attackingPlayer.playerLabel, attackerPosition))
            if (defender.hp <= 0) add(DestroyCreatureStep(defendingPlayer.playerLabel, defenderPosition))
        }
    }
}