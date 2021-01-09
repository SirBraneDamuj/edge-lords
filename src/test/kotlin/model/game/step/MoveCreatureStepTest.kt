package model.game.step

import model.game.*
import model.game.step.core.MoveCreatureStep
import model.game.step.spell.MagicCrystalStep
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MoveCreatureStepTest {
    @Test
    fun `it moves the creature`() {
        val game = Games.createFakeGame()
        val player = game.activePlayer
        val fromPosition = MASTER_STARTING_POSITION
        val toPosition = Position.FRONT_FOUR
        val step = MoveCreatureStep(player.playerLabel, fromPosition, toPosition)
        step.perform(game)
        assertEquals(toPosition, player.master().position)
    }

    @Test
    fun `it swaps places if there was already a creature there the creature`() {
        val game = Games.createFakeGame()
        val player = game.activePlayer
        val fromPosition = MASTER_STARTING_POSITION
        val toPosition = Position.FRONT_FOUR
        val natial = Natials.summonFromCardToPosition(
            GameNatialCard("123", "Ae-Ferrion", 3),
            toPosition,
            false
        )
        player.creatures = player.creatures + natial

        val step = MoveCreatureStep(player.playerLabel, fromPosition, toPosition)
        step.perform(game)

        assertEquals(toPosition, player.master().position)
        assertEquals(fromPosition, natial.position)
    }

    @Test
    fun `it returns a magic crystal step if one was at the destination`() {
        val game = Games.createFakeGame()
        val player = game.activePlayer
        val fromPosition = MASTER_STARTING_POSITION
        val toPosition = Position.FRONT_FOUR
        player.magicCrystals = player.magicCrystals + toPosition

        val step = MoveCreatureStep(player.playerLabel, fromPosition, toPosition)
        val nextStep = step.perform(game).single() as MagicCrystalStep

        assertEquals(toPosition, player.master().position)
        assertEquals(nextStep.playerLabel, player.playerLabel)
        assertEquals(nextStep.position, toPosition)
    }
}

