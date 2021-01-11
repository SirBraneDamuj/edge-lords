package server

import io.javalin.Javalin
import model.Cards

fun main(args: Array<String>) {
    val app = Javalin.create().start(7000)
    app.get("/cards") { ctx ->
        val response = mapOf(
            "masters" to Cards.masters,
            "natials" to Cards.natials,
            "spells" to Cards.spells
        )
        ctx.json(response)
    }
    app.get("/") { ctx -> ctx.result("Hello World") }
}
