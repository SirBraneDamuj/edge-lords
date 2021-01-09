package model.game.step

import model.game.Games
import model.game.step.core.CombatStep
import model.game.step.core.DestroyCreatureStep
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CombatStepTest {
    @Test
    fun `returns one destroycreaturestep when the defender is reduced to 0 hp`() {
        val game = Games.createFakeGame()
        val attacker = game.activePlayer.master()
        attacker.attack = 999999
        val defender = game.inactivePlayer.master()
        defender.attack = 4
        val step = CombatStep(game.activePlayerLabel, attacker.position, defender.position)
        val nextStep = step.perform(game).single() as DestroyCreatureStep
        assertEquals(game.activePlayerLabel.other, nextStep.playerLabel)
        assertEquals(defender.position, nextStep.position)
    }

    @Test
    fun `returns one destroycreaturestep when the attacker is reduced to 0 hp`() {
        val game = Games.createFakeGame()
        val attacker = game.activePlayer.master()
        attacker.attack = 4
        val defender = game.inactivePlayer.master()
        defender.attack = 999999
        val step = CombatStep(game.activePlayerLabel, attacker.position, defender.position)
        val nextStep = step.perform(game).single() as DestroyCreatureStep
        assertEquals(game.activePlayerLabel, nextStep.playerLabel)
        assertEquals(attacker.position, nextStep.position)
    }

    @Test
    fun `returns two destroycreaturesteps when both the attacker and defender are reduced to 0 hp`() {
        val game = Games.createFakeGame()
        val attacker = game.activePlayer.master()
        attacker.attack = 99999
        val defender = game.inactivePlayer.master()
        defender.attack = 999999
        val step = CombatStep(game.activePlayerLabel, attacker.position, defender.position)
        val nextSteps = step.perform(game).map { it as DestroyCreatureStep }
        nextSteps.single { it.playerLabel == game.activePlayerLabel }
        nextSteps.single { it.playerLabel == game.activePlayerLabel.other }
    }
}
