package server.service

import model.Cards
import model.card.CardType
import server.error.InvalidCardError
import server.model.Deck
import javax.inject.Inject

class DeckService @Inject constructor() {
    fun constructDeck(
        deck: Deck
    ): model.card.Deck =
        model.card.Deck(
            name = deck.name,
            master = Cards.getMasterByName(deck.master)
                ?: throw InvalidCardError(CardType.MASTER, deck.master),
            cards = deck.cards.flatMap { deckCard ->
                (1..deckCard.count).map {
                    Cards.getCard(deckCard.name)
                        ?: throw InvalidCardError(null, deckCard.name)
                }
            }
        )
}
