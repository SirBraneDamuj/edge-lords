import model.game.ActionExecutor
import model.game.Games
import model.game.action.EndTurnAction

fun main() {
    val game = Games.createFakeGame()
    repeat(5) {
        println("${game.activePlayer.name}'s turn")
        ActionExecutor.performAction(
            game,
            EndTurnAction(game.activePlayerLabel)
        )
    }
    println("${game.activePlayer.name}'s turn")
}

object ResourceLoader {
    fun getResource(path: String) = this::class.java.getResource(path).readText()
}

