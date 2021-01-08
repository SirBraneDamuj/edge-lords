package model.game.step

import model.MAGIC_CRYSTAL_ATTACK_INCREASE
import model.MAGIC_CRYSTAL_HP_INCREASE
import model.MAGIC_CRYSTAL_MANA_RESTORATION
import model.game.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MagicCrystalStepTest {
    @Test
    fun `it powers up the creature that uses it`() {
        val game = Games.createFakeGame()
        val player = game.activePlayer
        val master = player.master()
        master.hp = 6
        val masterCopy = master.copy()
        player.magicCrystals = player.magicCrystals + MASTER_STARTING_POSITION

        val step = MagicCrystalStep(game.activePlayerLabel, MASTER_STARTING_POSITION)
        step.perform(game)

        assertEquals(masterCopy.hp + MAGIC_CRYSTAL_HP_INCREASE, master.hp)
        assertEquals(masterCopy.maxHp + MAGIC_CRYSTAL_HP_INCREASE, master.maxHp)
        assertEquals(masterCopy.attack + MAGIC_CRYSTAL_ATTACK_INCREASE, master.attack)
    }

    @Test
    fun `it removes the magic crystal`() {
        val game = Games.createFakeGame()
        val player = game.activePlayer
        player.magicCrystals = player.magicCrystals + MASTER_STARTING_POSITION

        val step = MagicCrystalStep(game.activePlayerLabel, MASTER_STARTING_POSITION)
        step.perform(game)

        assertTrue(player.magicCrystals.isEmpty())
    }

    @Test
    fun `it enqueues a mana restore step`() {
        val game = Games.createFakeGame()
        val player = game.activePlayer
        player.magicCrystals = player.magicCrystals + MASTER_STARTING_POSITION

        val step = MagicCrystalStep(game.activePlayerLabel, MASTER_STARTING_POSITION)
        val nextStep = step.perform(game).single() as IncrementAndRestoreManaStep
        assertEquals(game.activePlayerLabel, nextStep.playerLabel)
        assertEquals(MAGIC_CRYSTAL_MANA_RESTORATION, nextStep.amountRestored)
    }
}
