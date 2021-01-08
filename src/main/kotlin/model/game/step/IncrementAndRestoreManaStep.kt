package model.game.step

import model.MAX_MAX_MANA
import model.game.Game
import model.game.PlayerLabel

class IncrementAndRestoreManaStep(
    val playerLabel: PlayerLabel,
    val amountRestored: Int? = null
) : GameStep {

    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        player.incrementManaAndRestore(amountRestored ?: MAX_MAX_MANA)
        return emptyList()
    }

}
