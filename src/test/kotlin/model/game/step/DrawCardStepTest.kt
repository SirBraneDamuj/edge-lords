package model.game.step

import model.game.GameCard
import model.game.Games
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DrawCardStepTest {
    @Test
    fun `it takes the top card from the player's deck and puts it in their hand`() {
        val game = Games.createFakeGame()
        val player = game.activePlayer
        val playerCopy = player.copy(
            hand = player.hand.map(GameCard::toCopy),
            deck = player.deck.map(GameCard::toCopy)
        )
        val step = DrawCardStep(game.activePlayerLabel)
        val nextSteps = step.perform(game)
        assertTrue(nextSteps.isEmpty())
        assertEquals(playerCopy.deck.first(), player.hand.last())
        assertEquals(playerCopy.deck[1], player.deck.first())
        assertEquals(playerCopy.hand.count() + 1, player.hand.count())
        assertEquals(playerCopy.deck.count() - 1, player.deck.count())
        assertTrue(player.hand.containsAll(playerCopy.hand))
    }
}
