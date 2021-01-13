package server

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Component
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import server.config.DatabaseModule
import server.config.ObjectMapperModule
import server.router.*
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
        val app = Javalin.create().start(7000)
        gameController.initRoutes(app)
        cardController.initRoutes(app)
        userController.initRoutes(app)
        deckController.initRoutes(app)
        loginController.initRoutes(app)
    }
}
