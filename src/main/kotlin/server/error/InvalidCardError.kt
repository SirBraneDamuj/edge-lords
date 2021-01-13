package server.error

import model.card.CardType
import java.lang.RuntimeException

class InvalidCardError(
    val cardType: CardType?,
    val cardName: String
) : RuntimeException(
    "The ${cardType ?: ""} card $cardName did not exist."
){
}
