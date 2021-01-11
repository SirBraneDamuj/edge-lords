package client

import model.game.*

fun main() {
    val game = Games.createFakeGame()
    val client = ConsoleClient(game)
    client.start()
}

class ConsoleClient(
    val game: Game
) {
    fun start() {
        var errorMessage: String? = null
        while (game.winner == Winner.NONE) {
            ConsolePrinter.printGame(game)
            val action = try {
                if (errorMessage != null) {
                    println(errorMessage)
                    errorMessage = null
                }
                ActionRecorder.recordAction(game)
            } catch (e: ActionInputException) {
                errorMessage = "Action input invalid: ${e.message}"
                continue
            }
            try {
                ActionExecutor.performAction(game, action)
            } catch (e: ActionValidationError) {
                errorMessage = "That action isn't valid: ${e.errors.error}"
            }
        }
        println("GAME SET")
        println("And the winner is...")
        Thread.sleep(1000)
        println(
            when (game.winner) {
                Winner.FIRST -> "${game.player(PlayerLabel.FIRST).name}!"
                Winner.SECOND -> "${game.player(PlayerLabel.SECOND).name}!"
                Winner.NONE -> "Well this is weird. I thought there was a winner but there isn't..."
            }
        )
    }
}
