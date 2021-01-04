package model.game.action

import Either
import model.game.Game

abstract class Action {
    abstract fun perform(game: Game): ActionResult

    protected fun invalidAction(error: String) = InvalidAction(ActionErrors(error))
}

typealias ActionResult = Either<Game, ActionErrors>
typealias ValidAction = Either.Left<Game>
typealias InvalidAction = Either.Right<ActionErrors>

data class ActionErrors(
    val error: String
)

enum class ActionType {
    SUMMON_NATIAL,
    MOVE_CREATURE,
    ACTIVATE_CREATURE,
    PLAY_SPELL,
    END_TURN;
}
