package model.game.step.effects

import model.game.Game
import model.game.GameCard
import model.game.PlayerLabel
import model.game.step.GameStep
import model.game.step.core.ShuffleStep
import util.toSingletonList

class ReturnCardToHand(
    val playerLabel: PlayerLabel,
    val card: GameCard
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        player.hand = player.hand + card
        return emptyList()
    }
}
