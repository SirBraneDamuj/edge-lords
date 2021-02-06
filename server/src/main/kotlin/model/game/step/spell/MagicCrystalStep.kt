package model.game.step.spell

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.core.IncrementAndRestoreManaStep
import util.toSingletonList

const val MAGIC_CRYSTAL_ATTACK_INCREASE = 1
const val MAGIC_CRYSTAL_HP_INCREASE = 1
const val MAGIC_CRYSTAL_MANA_RESTORATION = 1

class MagicCrystalStep(
    val playerLabel: PlayerLabel,
    val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(position)
            ?: error("no creature at that position... was the action validated?")
        creature.attack += MAGIC_CRYSTAL_ATTACK_INCREASE
        creature.increaseMaxHp(MAGIC_CRYSTAL_HP_INCREASE)
        player.magicCrystals = player.magicCrystals - position
        return IncrementAndRestoreManaStep(
            playerLabel = playerLabel,
            amountRestored = MAGIC_CRYSTAL_MANA_RESTORATION
        ).toSingletonList()
    }
}
