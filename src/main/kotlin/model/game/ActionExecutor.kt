package model.game

import model.game.action.*
import model.game.action.invalidAction

object ActionExecutor {
    fun performAction(
        game: Game,
        action: Action
    ): ActionResult {
        // pre-action stuff (just turn checking for now)
        if (game.activePlayerLabel != action.playerLabel) {
            return invalidAction("It is not ${action.playerLabel}'s turn")
        }

        // action
        return action.perform(game)
    }
}
