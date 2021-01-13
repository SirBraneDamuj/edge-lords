package server.router

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.http.Context
import server.dto.CreateDeckRequest
import server.repository.DeckRepository
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
            }
        }
    }

    fun createDeck(context: Context) {
        val body = context.bodyAsClass(CreateDeckRequest::class.java)
        val deck = deckRepository.createDeck(
            name = body.name,
            master = body.master,
            cards = body.cards,
            ownerId = context.attribute("userId")!!
        )
        context.status(201)
        context.json(deck)
    }
}
