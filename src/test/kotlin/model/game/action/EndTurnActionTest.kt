package model.game.action

import model.game.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class EndTurnActionTest {

    @Test
    fun `it works`() {
        val game = Games.createFakeGame()
        val action = EndTurnAction(PlayerLabel.FIRST)
        val startTurn = game.turn
        val newPlayer = game.inactivePlayer
        val newPlayerCopy = newPlayer.copy(
            hand = newPlayer.hand.map(GameCard::toCopy),
            deck = newPlayer.deck.map(GameCard::toCopy)
        )

        val result = action.perform(game)
        assertTrue(result is ValidAction)

        assertEquals(startTurn + 1, game.turn)

        assertEquals(newPlayerCopy.hand.count() + 1, game.activePlayer.hand.count())
        assertTrue(game.activePlayer.hand.containsAll(newPlayerCopy.hand))
        val drawnCard = (game.activePlayer.hand - newPlayerCopy.hand).single()
        assertEquals(newPlayerCopy.deck.first(), drawnCard)

        assertEquals(newPlayerCopy.maxMana + 1, game.activePlayer.mana)
        assertEquals(newPlayerCopy.maxMana + 1, game.activePlayer.maxMana)
    }

}
