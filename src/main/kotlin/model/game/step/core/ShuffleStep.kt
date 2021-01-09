package model.game.step.core

import model.game.Game
import model.game.PlayerLabel
import model.game.step.GameStep

class ShuffleStep(
    val playerLabel: PlayerLabel
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        player.deck = player.deck.shuffled()
        return emptyList()
    }
}
