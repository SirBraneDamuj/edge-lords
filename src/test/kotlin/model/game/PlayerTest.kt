package model.game

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PlayerTest {

    @Test
    fun `creatureAtPosition returns the creature for the player at the given position`() {
        val player = Games.createFakeGame().activePlayer
        val position = Position.FRONT_THREE
        val creature = Natials.summonFromCardToPosition(
            gameCard = GameNatialCard("Ae-Ferrion", 1),
            position = position,
            magicCrystal = false
        )
        player.creatures = player.creatures + creature
        assertEquals(creature, player.creatureAtPosition(position))
    }

    @Test
    fun `creatureAtPosition returns null if no creature exists at the given position`() {
        val player = Games.createFakeGame().activePlayer
        val position = Position.FRONT_ONE
        assertTrue(player.creatures.none { it.position == position })
        assertNull(player.creatureAtPosition(position))
    }

    @Test
    fun `creatureAtPosition throws error if more than one creature exists at a position`() {
        val player = Games.createFakeGame().activePlayer
        val position = Position.BACK_ONE
        val creature = Natials.summonFromCardToPosition(
            gameCard = GameNatialCard("Ae-Ferrion", 1),
            position = position,
            magicCrystal = false
        )
        player.creatures = player.creatures + creature + creature.copy()
        assertThrows(IllegalArgumentException::class.java) { player.creatureAtPosition(position) }
    }

    @Test
    fun `incrementAndRestoreMana with no args when the player has less than their max mana restores to the new max`() {
        val player = Games.createFakeGame().activePlayer
        player.mana = player.maxMana - 1
        val playerCopy = player.copy()

        player.incrementManaAndRestore()

        assertEquals(playerCopy.maxMana + 1, player.maxMana)
        assertEquals(player.maxMana, player.mana)
    }

    @Test
    fun `incrementAndRestoreMana with no args when the player has their max mana increases it to the new max`() {
        val player = Games.createFakeGame().activePlayer
        val playerCopy = player.copy()

        player.incrementManaAndRestore()

        assertEquals(playerCopy.maxMana + 1, player.maxMana)
        assertEquals(player.maxMana, player.mana)
    }

    @Test
    fun `incrementAndRestoreMana with an arg increases max by 1 but only restores as much mana as specified`() {
        val player = Games.createFakeGame().activePlayer
        val playerCopy = player.copy()
        val amountRestored = 1

        player.incrementManaAndRestore(amountRestored)

        assertEquals(playerCopy.maxMana + 1, player.maxMana)
        assertEquals(playerCopy.mana + amountRestored, player.mana)
    }
}
