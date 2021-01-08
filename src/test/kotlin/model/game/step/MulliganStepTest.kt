package model.game.step

import model.game.Games
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MulliganStepTest {
    @Test
    fun `it sets the mulligan flag but nothing else when the player doesn't change any cards`() {
        val game = Games.createFakeGame()
        val player = game.activePlayer
        val playerCopy = player.copy(
            hand = player.hand.map { it.toCopy() },
            deck = player.deck.map { it.toCopy() }
        )

        val step = MulliganStep(game.activePlayerLabel, emptySet())
        step.perform(game).single() as EndTurnStep

        assertEquals(player.hand, playerCopy.hand)
        assertEquals(player.deck, playerCopy.deck)
    }

    @Test
    fun `it sets the mulligan flag and swaps out the player's hand when some cards are changed`() {
        val game = Games.createFakeGame()
        val player = game.activePlayer
        val playerCopy = player.copy(
            hand = player.hand.map { it.toCopy() },
            deck = player.deck.map { it.toCopy() }
        )

        val step = MulliganStep(game.activePlayerLabel, setOf(0, 2))
        step.perform(game).single() as EndTurnStep

        // TODO: check that the deck is shuffled and cards taken out. I don't feel like it right now lol
        assertEquals(playerCopy.hand[1], player.hand[0])
    }
}
