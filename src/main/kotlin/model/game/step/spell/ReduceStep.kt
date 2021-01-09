package model.game.step.spell

import model.game.Game
import model.game.PlayerLabel
import model.game.step.GameStep
import util.whenIts

class ReduceStep(
    val playerLabel: PlayerLabel,
    val handIndex: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val card = player.hand[handIndex]
        card.manaCost = card.manaCost.whenIts(
            odd = { (it / 2) + 1 },
            even = { it / 2 }
        )
        return emptyList()
    }
}
