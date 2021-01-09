package model.game.step.core

import model.game.Game
import model.game.PlayerLabel
import model.game.Winner
import model.game.step.GameStep

class VictoryStep(
    private val winningPlayer: PlayerLabel
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        game.winner = Winner.fromPlayerLabel(winningPlayer)
        return emptyList()
    }
}
