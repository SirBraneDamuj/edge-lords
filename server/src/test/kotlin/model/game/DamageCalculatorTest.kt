package model.game

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DamageCalculatorTest {
    @Test
    fun `calculateCombatDamage lowers the defender's attack power by 1`() {
        val attacker = Masters.masterFromCardToPosition(
            GameMasterCard("123", "Paladin"),
            MASTER_STARTING_POSITION
        )
        attacker.attack = 5
        val defender = Masters.masterFromCardToPosition(
            GameMasterCard("123", "Paladin"),
            MASTER_STARTING_POSITION
        )
        defender.attack = 3
        val (attackerDamage, defenderDamage) = DamageCalculator.calculateCombatDamage(attacker, defender)
        assertEquals(5, attackerDamage)
        assertEquals(2, defenderDamage)
    }

    @Test
    fun `calculateCombatDamage considers elemental advantage`() {
        val marme = Natials.summonFromCardToPosition(
            GameNatialCard("123", "Marme", 1),
            MASTER_STARTING_POSITION,
            false
        )
        marme.attack = 5
        val giaBro = Natials.summonFromCardToPosition(
            GameNatialCard("123", "Gia-Bro", 1),
            MASTER_STARTING_POSITION,
            false
        )
        giaBro.attack = 5
        val (marmeAttackDamage, giaBroDefendDamage) = DamageCalculator.calculateCombatDamage(marme, giaBro)
        assertEquals(3, marmeAttackDamage)
        assertEquals(6, giaBroDefendDamage)
        val (giaBroAttackDamage, marmeDefendDamage) = DamageCalculator.calculateCombatDamage(giaBro, marme)
        assertEquals(7, giaBroAttackDamage)
        assertEquals(2, marmeDefendDamage)
    }
}