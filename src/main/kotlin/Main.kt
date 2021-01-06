import com.charleskorn.kaml.Yaml
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import model.Cards
import model.card.*
import model.game.ActionExecutor
import model.game.Game
import model.game.PlayerLabel
import model.game.Players
import model.game.action.EndTurnAction

@Serializable
data class LoadedCard(
    val name: String,
    val type: CardType,
    val count: Int
)

@Serializable
data class LoadedDeck(
    val name: String,
    val master: String,
    val cards: List<LoadedCard>
)

fun main() {
    val deck1= loadDeck("/decks/deck1.yml")
    val deck2 = loadDeck("/decks/deck2.yml")
    val game = Game(
        players = mapOf(
            PlayerLabel.FIRST to Players.createPlayerForDeck(
                name = "Rean",
                label = PlayerLabel.FIRST,
                deck = deck1
            ),
            PlayerLabel.SECOND to Players.createPlayerForDeck(
                name = "Juna",
                label = PlayerLabel.SECOND,
                deck = deck2
            )
        ),
        turn = -1
    )
    repeat(5) {
        println("${game.activePlayer.name}'s turn")
        ActionExecutor.performAction(
            game,
            EndTurnAction(game.activePlayerLabel)
        )
    }
    println("${game.activePlayer.name}'s turn")
}

fun loadDeck(filename: String): Deck {
    val loadedDeck = Yaml.default.decodeFromString<LoadedDeck>(ResourceLoader.getResource(filename))
    loadedDeck.cards.sumBy(LoadedCard::count)
        .takeIf { it == 20 }
        ?: error("this deck ain't got the right number of cards dude")
    loadedDeck.cards
        .takeIf { it.all { c -> c.count in (1..3) } }
        ?: error("each card can only have between 1 and 3 copies my friend")
    val cards = loadedDeck.cards.flatMap { loadedCard ->
        (1..loadedCard.count).map {
            Cards.getCardByNameAndType(loadedCard.name, loadedCard.type)
                ?: error("that card don't exist dude")
        }
    }
    val master = Cards.getMasterByName(loadedDeck.master)
        ?: error("that master don't exist dude")
    return Deck(
        name = loadedDeck.name,
        master = master,
        cards = cards
    )
}

object ResourceLoader {
    fun getResource(path: String) = this::class.java.getResource(path).readText()
}

