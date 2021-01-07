package model.card

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import model.Cards

data class Deck(
    val name: String,
    val master: MasterCard,
    val cards: List<Card>
)

object Decks {
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
}

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
