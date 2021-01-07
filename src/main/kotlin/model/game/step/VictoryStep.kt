package model.game.step

import model.game.Game
import model.game.PlayerLabel
import model.game.Winner

class VictoryStep(
    private val winningPlayer: PlayerLabel
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        game.winner = Winner.fromPlayerLabel(winningPlayer)
        return emptyList()
    }
}
