package model.game.step

import model.game.Games
import model.game.PlayerLabel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class EndTurnStepTest {
    @Test
    fun `it increments the turn counter`() {
        val game = Games.createFakeGame()
        game.turn = 93
        val step = EndTurnStep()
        step.perform(game)
        assertEquals(94, game.turn)
    }

    @Test
    fun `it does not enqueue any other steps when a player hasn't mulliganed`() {
        val game = Games.createFakeGame()
        game.turn = 77
        assertTrue(game.players.values.none { it.mulliganed })
        val step = EndTurnStep()

        var nextSteps = step.perform(game)
        assertEquals(78, game.turn)
        assertTrue(nextSteps.isEmpty())

        game.player(PlayerLabel.FIRST).mulliganed = true
        nextSteps = step.perform(game)
        assertEquals(79, game.turn)
        assertTrue(nextSteps.isEmpty())
    }

    @Test
    fun `enqueues a start turn step when both players have mulliganed`() {
        val game = Games.createFakeGame()
        game.players.values.forEach {
            it.mulliganed = true
        }
        val step = EndTurnStep()
        val nextStep = step.perform(game).single() as StartTurnStep
        assertEquals(game.activePlayerLabel, nextStep.playerLabel)
    }
}
