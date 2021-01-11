package model.card

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import model.Cards
import util.ResourceLoader

data class Deck(
    val name: String,
    val master: MasterCard,
    val cards: List<Card>
)

object Decks {
    private val objectMapper = ObjectMapper(YAMLFactory()).registerKotlinModule()

    fun loadDeck(filename: String): Deck {
        val loadedDeck = objectMapper.readValue<LoadedDeck>(ResourceLoader.getResource(filename))
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

data class LoadedCard(
    val name: String,
    val type: CardType,
    val count: Int
)

data class LoadedDeck(
    val name: String,
    val master: String,
    val cards: List<LoadedCard>
)
