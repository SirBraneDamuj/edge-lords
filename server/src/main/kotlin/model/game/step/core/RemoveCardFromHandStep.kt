package model.game.step.core

import model.game.Game
import model.game.PlayerLabel
import model.game.step.GameStep

class RemoveCardFromHandStep(
    val playerLabel: PlayerLabel,
    val handIndex: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        player.hand = player.hand.filterIndexed { i, _ -> i != handIndex }
        return emptyList()
    }
}
