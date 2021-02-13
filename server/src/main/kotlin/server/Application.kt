package server

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Component
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import server.card.CardController
import server.config.DatabaseModule
import server.config.ObjectMapperModule
import server.deck.DeckController
import server.error.*
import server.game.GameController
import server.session.LoginController
import server.user.UserController
import javax.inject.Inject
import javax.inject.Singleton

@Component(
    modules = [
        ObjectMapperModule::class,
        DatabaseModule::class,
    ]
)
@Singleton
interface AppComponent {
    fun application(): Application
}

class Application @Inject constructor(
    private val gameController: GameController,
    private val cardController: CardController,
    private val userController: UserController,
    private val loginController: LoginController,
    private val deckController: DeckController,
    private val objectMapper: ObjectMapper
) {
    fun start() {
        JavalinJackson.configure(objectMapper)
        val app = Javalin.create {
            it.addStaticFiles("/public")
            it.addSinglePageRoot("/", "/public/index.html")
        }.start(System.getenv("PORT")?.toInt() ?: 7000)
        app.exception(RecordNotFoundError::class.java) { _, context ->
            context.status(404)
        }
        app.exception(InvalidRequestError::class.java) { _, context ->
            context.status(400)
        }
        // todo this error doesn't belong at this level
        app.exception(InvalidCardError::class.java) { e, context ->
            context.status(400)
            context.json(
                mapOf("error" to e.message)
            )
        }
        app.exception(InvalidSessionError::class.java) { _, context ->
            context.status(403)
        }
        app.exception(UnauthenticatedError::class.java) { _, context ->
            context.status(401)
        }
        gameController.initRoutes(app)
        cardController.initRoutes(app)
        userController.initRoutes(app)
        deckController.initRoutes(app)
        loginController.initRoutes(app)
    }
}
