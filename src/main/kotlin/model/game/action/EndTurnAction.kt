package model.game.action

import model.game.Game
import model.game.PlayerLabel

class EndTurnAction(
    override val playerLabel: PlayerLabel
) : Action {
    override fun perform(game: Game): ActionResult {
        game.turn++
        val drawnCard = game.activePlayer.deck.take(1)
        game.activePlayer.hand = game.activePlayer.hand + drawnCard
        game.activePlayer.deck = game.activePlayer.deck.drop(1)
        game.activePlayer.incrementManaAndRestore()
        return ValidAction(game)
    }
}
