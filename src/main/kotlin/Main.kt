import client.ConsoleClient
import client.ConsolePrinter
import model.game.Games

fun main() {
    val game = Games.createFakeGame()
    val client = ConsoleClient(game)
    client.start()
}

object ResourceLoader {
    fun getResource(path: String) = this::class.java.getResource(path).readText()
}

