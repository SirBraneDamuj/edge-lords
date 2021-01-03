import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.*

fun main() {
    val card = Natial(
        name = "Ae-Ferrion",
        manaCost = 3,
        attack = 2,
        hp = 2,
        maxHp = 2,
        range = Range.RANGED,
        speed = Speed.NORMAL,
        element = Element.EARTH,
        skillText = "",
        abilityText = "",
        abilityState = AbilityState.NONE,
        activationState = ActivationState.NOT_ACTIVATED
    )
    val json = Json.encodeToString(card)
    println(json)
    println(Json.encodeToString(Json.decodeFromString<Natial>(json)))
}
