package model.game.action

import model.game.Game
import model.game.PlayerLabel
import model.game.step.core.EndTurnStep

class EndTurnAction(
    override val playerLabel: PlayerLabel
) : Action {
    override fun validate(game: Game): ActionResult {
        return ValidAction(
            EndTurnStep()
        )
    }
}
