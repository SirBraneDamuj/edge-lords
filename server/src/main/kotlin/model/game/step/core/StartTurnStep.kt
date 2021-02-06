package model.game.step.core

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

class StartTurnStep(
    val playerLabel: PlayerLabel
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return listOf(
            DrawCardStep(playerLabel),
            ReadyCreaturesStep(playerLabel, Position.allPositions),
            IncrementAndRestoreManaStep(playerLabel)
        )
    }
}
