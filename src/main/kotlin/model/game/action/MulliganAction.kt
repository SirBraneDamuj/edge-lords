package model.game.action

import model.game.Game
import model.game.PlayerLabel

class MulliganAction(
    override val playerLabel: PlayerLabel,
    private val changeIndices: Set<Int>
) : Action{
    override fun perform(game: Game): ActionResult {
        if (changeIndices.count() !in (0..3)) {
            return invalidAction("Too many or too few cards selected for change")
        }
        val player = game.players.getValue(playerLabel)
        if (player.mulliganed) {
            return invalidAction("this player already performed their mulligan")
        }
        player.mulliganed = true
        if (changeIndices.isEmpty()) return ValidAction(game)
        val changedCards = changeIndices.map { player.hand[it] }
        player.hand -= changedCards
        player.deck += changedCards
        player.deck = player.deck.shuffled()
        player.hand += player.deck.take(changeIndices.count())
        player.deck = player.deck.drop(changeIndices.count())
        game.turn++
        return ValidAction(game)
    }
}
