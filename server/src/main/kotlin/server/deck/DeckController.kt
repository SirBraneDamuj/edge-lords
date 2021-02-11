package server.deck

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context
import server.error.InvalidRequestError
import server.error.RecordNotFoundError
import server.session.AuthHandler
import javax.inject.Inject

class DeckController @Inject constructor(
    private val deckRepository: DeckRepository,
    private val authHandler: AuthHandler
) {
    fun initRoutes(app: Javalin) {
        app.before("/decks", authHandler)
        app.before("/decks/*", authHandler)
        app.routes {
            path("decks") {
                post(this::createDeck)
                put(":id", this::updateDeck)
                get(":id", this::getDeck)
            }
        }
    }

    fun createDeck(context: Context) {
        val body = context.bodyAsClass(CreateDeckRequest::class.java)
        deckRepository.createDeck(
            name = body.name,
            master = body.master,
            cards = body.cards,
            ownerId = context.attribute("userId")!!
        )
        context.status(201)
        context.json(mapOf("success" to true))
    }

    fun updateDeck(context: Context) {
        val body = context.bodyAsClass(CreateDeckRequest::class.java)
        val deckId = context.pathParam("id")
        deckRepository.updateDeck(
            id = deckId.toInt(),
            name = body.name,
            master = body.master,
            cards = body.cards,
            ownerId = context.attribute("userId")!!
        )
        context.json(mapOf("success" to true))
    }

    fun getDeck(context: Context) {
        val deck = deckRepository.findById(context.pathParam("id").toInt())
            ?: throw RecordNotFoundError()
        context.json(deck)
    }
}
