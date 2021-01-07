package model.game.step

import model.game.Game
import model.game.PlayerLabel

class StartTurnStep(
    val playerLabel: PlayerLabel
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return listOf(
            DrawCardStep(playerLabel),
            IncrementAndRestoreManaStep(playerLabel)
        )
    }
}
