import client.ConsoleClient
import model.game.ActionExecutor
import model.game.Games
import model.game.Position
import model.game.action.EndTurnAction

fun main() {
    val game = Games.createFakeGame()
    val client = ConsoleClient()
    client.printGame(game)
}

object ResourceLoader {
    fun getResource(path: String) = this::class.java.getResource(path).readText()
}

