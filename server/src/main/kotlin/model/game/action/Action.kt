package model.game.action

import util.Either
import model.game.Game
import model.game.PlayerLabel
import model.game.step.GameStep

interface Action {
    val playerLabel: PlayerLabel
    fun validate(game: Game): ActionResult
}

internal fun invalidAction(error: String) = InvalidAction(ActionErrors(error))

typealias ActionResult = Either<GameStep, ActionErrors>
typealias ValidAction = Either.Left<GameStep>
typealias InvalidAction = Either.Right<ActionErrors>

data class ActionErrors(
    val error: String
)
