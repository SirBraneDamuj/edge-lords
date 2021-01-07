package model.game.step

import model.game.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CombatStepTest {
    @Test
    fun `lowers the defender's attack power by 1`() {
        val game = Games.createFakeGame()
        val attacker = game.activePlayer.creatureAtPosition(MASTER_STARTING_POSITION)!!
        attacker.hp = 10
        attacker.attack = 5
        val defender = game.inactivePlayer.creatureAtPosition(MASTER_STARTING_POSITION)!!
        defender.hp = 10
        defender.attack = 8
        val step = CombatStep(
            attackingPlayerLabel = game.activePlayerLabel,
            attackerPosition = MASTER_STARTING_POSITION,
            defenderPosition = MASTER_STARTING_POSITION
        )
        val nextSteps = step.perform(game)
        assertTrue(nextSteps.isEmpty())
        assertEquals(3, attacker.hp)
        assertEquals(5, defender.hp)
    }

    @Test
    fun `considers the elemental advantage`() {

    }
}
