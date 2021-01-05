package model.game.action

import Either
import model.game.Game
import model.game.PlayerLabel

interface Action {
    val playerLabel: PlayerLabel
    fun perform(game: Game): ActionResult
}

internal fun invalidAction(error: String) = InvalidAction(ActionErrors(error))

typealias ActionResult = Either<Game, ActionErrors>
typealias ValidAction = Either.Left<Game>
typealias InvalidAction = Either.Right<ActionErrors>

data class ActionErrors(
    val error: String
)

enum class ActionType {
    MULLIGAN,
    END_TURN,
    SUMMON_NATIAL,
    MOVE_CREATURE,
    ACTIVATE_CREATURE,
    ATTACK_CREATURE,
    PLAY_SPELL,
}
