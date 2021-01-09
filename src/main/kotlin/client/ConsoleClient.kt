package client

import model.game.ActionExecutor
import model.game.ActionValidationError
import model.game.Game
import model.game.Winner

class ConsoleClient(
    val game: Game
) {
    fun start() {
        while (game.winner == Winner.NONE) {
            ConsolePrinter.printGame(game)
            val action = try {
                ActionRecorder.recordAction(game)
            } catch (e: ActionInputException) {
                println("Action input invalid: ${e.message}")
                continue
            }
            try {
                ActionExecutor.performAction(game, action)
            } catch (e: ActionValidationError) {
                println("That action isn't valid: ${e.errors.error}")
            }
        }
    }
}
