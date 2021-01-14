package server.card

import io.javalin.Javalin
import io.javalin.http.Context
import model.Cards
import javax.inject.Inject

class CardController @Inject constructor() {
    fun initRoutes(app: Javalin) {
        app.get("/cards", this::getAllCards)
    }

    fun getAllCards(context: Context) {
        val response = mapOf(
            "masters" to Cards.masters,
            "natials" to Cards.natials,
            "spells" to Cards.spells
        )
        context.json(response)
    }
}
