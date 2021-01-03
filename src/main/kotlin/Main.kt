import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.Card
import model.CardType

fun main() {
    val card = Card("testcard", CardType.NATIAL)
    val json = Json.encodeToString(card)
    println(json)
    println(Json.encodeToString(Json.decodeFromString<Card>(json)))
}