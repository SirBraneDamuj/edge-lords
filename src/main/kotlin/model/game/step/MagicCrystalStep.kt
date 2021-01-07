package model.game.step

import model.MAGIC_CRYSTAL_ATTACK_INCREASE
import model.MAGIC_CRYSTAL_HP_INCREASE
import model.MAGIC_CRYSTAL_MANA_RESTORATION
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import util.toSingletonList

class MagicCrystalStep(
    private val playerLabel: PlayerLabel,
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(position)
            ?: error("no creature at that position... was the action validated?")
        creature.attack += MAGIC_CRYSTAL_ATTACK_INCREASE
        creature.maxHp += MAGIC_CRYSTAL_HP_INCREASE
        creature.hp += MAGIC_CRYSTAL_HP_INCREASE
        player.magicCrystals = player.magicCrystals - position
        return IncrementAndRestoreManaStep(
            playerLabel = playerLabel,
            amountRestored = MAGIC_CRYSTAL_MANA_RESTORATION
        ).toSingletonList()
    }
}
