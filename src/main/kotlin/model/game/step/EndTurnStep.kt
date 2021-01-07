package model.game.step

import model.game.Game
import util.toSingletonList

class EndTurnStep : GameStep {
    override fun perform(game: Game): List<GameStep> {
        game.turn++
        return if (game.players.values.all { it.mulliganed }) {
            StartTurnStep(
                playerLabel = game.activePlayerLabel
            ).toSingletonList()
        } else {
            emptyList()
        }
    }
}
