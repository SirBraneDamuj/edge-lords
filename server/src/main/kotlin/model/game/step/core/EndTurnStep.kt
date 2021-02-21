package model.game.step.core

import model.game.Game
import model.game.step.GameStep
import util.toSingletonList
import kotlin.math.max

class EndTurnStep : GameStep {
    override fun perform(game: Game): List<GameStep> {
        game.activePlayer.creatures.forEach {
            it.sealCount = max(it.sealCount - 1, 0)
        }
        game.turn++
        return if (game.players.values.all { it.mulliganed }) {
            StartTurnStep(playerLabel = game.activePlayerLabel).toSingletonList()
        } else {
            emptyList()
        }
    }
}
