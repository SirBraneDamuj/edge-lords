package model.game.step

import model.game.Game
import model.game.PlayerLabel

class DrawCardStep(
    private val playerLabel: PlayerLabel
) : GameStep {

    // TODO: what happens when they run out of cards? I think that means the other player wins?
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val drawnCard = player.deck.take(1)
        player.hand = player.hand + drawnCard
        player.deck = player.deck.drop(1)
        return emptyList()
    }

}
