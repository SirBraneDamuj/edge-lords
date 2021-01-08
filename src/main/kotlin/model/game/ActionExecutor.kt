package model.game

import model.game.action.Action
import model.game.action.ActionErrors
import model.game.action.InvalidAction
import model.game.action.ValidAction

class ActionValidationError(
    val errors: ActionErrors
) : RuntimeException("There was an error with this action.")
// TODO make this message better

object ActionExecutor {
    fun performAction(
        game: Game,
        action: Action
    ) {
        // pre-action stuff (just turn checking for now)
        if (game.activePlayerLabel != action.playerLabel) {
            throw ActionValidationError(
                ActionErrors("It is not ${action.playerLabel}'s turn")
            )
        }

        // action
        when (val result = action.validate(game)) {
            is ValidAction -> {
                val step = result.value
                val nextSteps = ArrayDeque(step.perform(game))
                while (nextSteps.isNotEmpty()) {
                    val nextStep = nextSteps.removeFirst()
                    nextSteps.addAll(
                        nextStep.perform(game)
                    )
                }
            }
            is InvalidAction -> {
                throw ActionValidationError(result.value)
            }
        }
    }
}
