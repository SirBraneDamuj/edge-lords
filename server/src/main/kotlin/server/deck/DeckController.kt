package server.deck

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context
import server.error.InvalidRequestError
import server.error.RecordNotFoundError
import server.session.AuthHandler
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Inject

class DeckController @Inject constructor(
    private val deckRepository: DeckRepository,
    private val authHandler: AuthHandler
) {
    fun initRoutes(app: Javalin) {
        app.before("/api/decks", authHandler)
        app.before("/api/decks/*", authHandler)
        app.routes {
            path("api/decks") {
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
        val deckId = try {
            UUID.fromString(context.pathParam("id"))
        } catch (e: IllegalArgumentException) {
            throw RecordNotFoundError()
        }
        deckRepository.updateDeck(
            id = deckId,
            name = body.name,
            master = body.master,
            cards = body.cards,
            ownerId = context.attribute("userId")!!
        )
        context.json(mapOf("success" to true))
    }

    fun getDeck(context: Context) {
        val deckId = try {
            UUID.fromString(context.pathParam("id"))
        } catch (e: IllegalArgumentException) {
            throw RecordNotFoundError()
        }
        val deck = deckRepository.findById(deckId)
            ?: throw RecordNotFoundError()
        context.json(deck)
    }
}
