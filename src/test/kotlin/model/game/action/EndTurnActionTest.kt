package model.game.action

import model.game.GameNatialCard
import model.game.GameSpellCard
import model.game.Games
import model.game.PlayerLabel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class EndTurnActionTest {

    @Test
    fun `it works`() {
        val game = Games.createFakeGame()
        val action = EndTurnAction(PlayerLabel.FIRST)
        val startTurn = game.turn
        val newPlayerStartingHand = game.inactivePlayer.hand.map {
            when (it) {
                is GameNatialCard -> it.copy()
                is GameSpellCard -> it.copy()
                else -> error("")
            }
        }

        val result = action.perform(game)
        assertTrue(result is ValidAction)

        val endTurn = game.turn
        assertEquals(startTurn + 1, endTurn)
        assertEquals(newPlayerStartingHand.count() + 1, game.activePlayer.hand.count())
        assertTrue(game.activePlayer.hand.containsAll(newPlayerStartingHand))
    }

}