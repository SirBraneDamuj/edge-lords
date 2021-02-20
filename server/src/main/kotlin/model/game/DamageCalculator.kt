package model.game

import model.COUNTERATTACKERS
import model.DEFENDER_STRENGTH_PENALTY
import kotlin.math.max

object DamageCalculator {
    fun calculateCombatDamage(
        attacker: Creature,
        defender: Creature
    ): Pair<Int, Int> {
        var attackerStrength = attacker.attack
        attackerStrength += attacker.element?.strengthModifierAgainst(defender.element) ?: 0
        var defenderStrength = defender.attack - DEFENDER_STRENGTH_PENALTY
        defenderStrength += defender.element?.strengthModifierAgainst(attacker.element) ?: 0
        defenderStrength += COUNTERATTACKERS.getOrDefault(defender.card.cardName, 0)
        return max(attackerStrength, 0) to max(defenderStrength, 0)
    }
}
