package model.game.step.core

import model.RANGED_COUNTERATTACKERS
import model.game.DamageCalculator
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.InterCreatureDamageStep

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
        attacker.activationState = attacker.activationState.stateAfterActing(attacker.card.cardName)

        // TODO: abilities which trigger when one Natial kills another should trigger here
        return mutableListOf<GameStep>().apply {
            if (!attacker.position.backRow || defender.card.cardName in RANGED_COUNTERATTACKERS) {
                this.add(InterCreatureDamageStep(
                    dealerPlayerLabel = defendingPlayer.playerLabel,
                    dealerPosition = defenderPosition,
                    receiverPosition = attackerPosition,
                    damageAmount = defenderStrength
                ))
            }
            this.add(InterCreatureDamageStep(
                dealerPlayerLabel = attackingPlayerLabel,
                dealerPosition = attackerPosition,
                receiverPosition = defenderPosition,
                damageAmount = attackerStrength
            ))
        }
    }
}
