package server.deck

import model.Cards
import model.card.CardType
import server.error.InvalidCardError
import javax.inject.Inject

class DeckService @Inject constructor() {
    fun constructDeck(
        deck: DeckDto,
    ): model.card.Deck =
        model.card.Deck(
            name = deck.name,
            playerId = deck.playerId.toString(),
            playerName = deck.playerName,
            master = Cards.getMasterByName(deck.master)
                ?: throw InvalidCardError(CardType.MASTER, deck.master),
            cards = deck.cards.flatMap { (name, count) ->
                (1..count).map {
                    Cards.getCard(name)
                        ?: throw InvalidCardError(null, name)
                }
            }
        )
}
