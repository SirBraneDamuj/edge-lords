package model.game

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class GameTest {
    @Test
    fun `activePlayerLabel returns FIRST when the turn counter is odd`() {
        val game = Games.createFakeGame()
        game.turn = 53
        assertEquals(PlayerLabel.FIRST, game.activePlayerLabel)
    }

    @Test
    fun `activePlayerLabel returns SECOND when the turn counter is odd`() {
        val game = Games.createFakeGame()
        game.turn = 998
        assertEquals(PlayerLabel.SECOND, game.activePlayerLabel)
    }

    @Test
    fun `activePlayer returns the FIRST player when the turn counter is odd`() {
        val game = Games.createFakeGame()
        game.turn = 999987
        assertEquals(game.players.getValue(PlayerLabel.FIRST), game.activePlayer)
    }

    @Test
    fun `activePlayer returns the SECOND player when the turn counter is even`() {
        val game = Games.createFakeGame()
        game.turn = 2
        assertEquals(game.players.getValue(PlayerLabel.SECOND), game.activePlayer)
    }

    @Test
    fun `inactivePlayer returns the FIRST player when the turn counter is even`() {
        val game = Games.createFakeGame()
        game.turn = 10
        assertEquals(game.players.getValue(PlayerLabel.FIRST), game.inactivePlayer)
    }

    @Test
    fun `inactivePlayer returns the SECOND player when the turn counter is odd`() {
        val game = Games.createFakeGame()
        game.turn = 17
        assertEquals(game.players.getValue(PlayerLabel.SECOND), game.inactivePlayer)
    }

    @Test
    fun `startOfRound returns null during mulligans`() {
        val game = Games.createFakeGame()
        game.turn = 1
        assertNull(game.startOfRound)
        game.turn = 2
        assertNull(game.startOfRound)
    }

    @Test
    fun `startOfRound returns 1 on the first player first turn`() {
        val game = Games.createFakeGame()
        game.turn = 3
        assertEquals(1, game.startOfRound)
    }

    @Test
    fun `startOfRound returns 3 on the first player third turn`() {
        val game = Games.createFakeGame()
        game.turn = 7
        assertEquals(3, game.startOfRound)
    }

    @Test
    fun `startOfRound always returns null on the second player's turn`() {
        val game = Games.createFakeGame()
        listOf(4, 6, 8, 10, 12, 14, 16).forEach {
            game.turn = it
            assertNull(game.startOfRound)
        }
    }

}
