package model.game.action

import model.game.Game

class EndTurnAction : Action() {
    override fun perform(game: Game): ActionResult {
        game.turn++
        return ValidAction(game)
    }
}
