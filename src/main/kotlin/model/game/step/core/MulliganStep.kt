package model.game.step.core

import model.game.Game
import model.game.PlayerLabel
import model.game.step.GameStep
import util.toSingletonList

class MulliganStep(
    val playerLabel: PlayerLabel,
    val changeIndices: Set<Int>
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.players.getValue(playerLabel)
        player.mulliganed = true
        if (changeIndices.isNotEmpty()) {
            // TODO: can this step be split up? it's certainly not atomic
            val changedCards = changeIndices.map { player.hand[it] }
            player.hand -= changedCards
            player.deck += changedCards
            player.deck = player.deck.shuffled()
            player.hand += player.deck.take(changeIndices.count())
            player.deck = player.deck.drop(changeIndices.count())
        }
        return EndTurnStep().toSingletonList()
    }
}
