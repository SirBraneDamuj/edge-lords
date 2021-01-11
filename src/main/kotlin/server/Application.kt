package server

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Component
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import model.Cards
import server.config.ObjectMapperModule
import server.router.CardController
import server.router.GameController
import javax.inject.Inject

@Component(
    modules = [
        ObjectMapperModule::class,
    ]
)
interface AppComponent {
    fun application(): Application
}

class Application @Inject constructor(
    private val gameController: GameController,
    private val cardController: CardController,
    private val objectMapper: ObjectMapper
) {
    fun start() {
        JavalinJackson.configure(objectMapper)
        val app = Javalin.create().start(7000)
        gameController.initRoutes(app)
        cardController.initRoutes(app)
    }
}
