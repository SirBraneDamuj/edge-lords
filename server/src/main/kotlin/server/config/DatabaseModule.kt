package server.config

import dagger.Module
import dagger.Provides
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import server.deck.Deck
import server.deck.DeckCard
import server.deck.DeckCards
import server.deck.Decks
import server.game.Games
import server.session.Sessions
import server.user.User
import server.user.Users
import java.net.URI
import javax.inject.Singleton

class Db private constructor() {
    val user = User
    val deck = Deck
    val deckCard = DeckCard

    companion object {
        fun connect(): Db {
            val databaseUrl = System.getenv("DATABASE_URL")
                ?: "sqlite:edge-lords.db"
            val jdbcUrl = if (databaseUrl.startsWith("postgres")) {
                val uri = URI(databaseUrl)
                val (username, password) = uri.userInfo.split(":")
                val host = uri.host
                val port = uri.port
                val path = uri.path
                "jdbc:postgresql://$host:$port$path?user=$username&password=$password"
            } else {
                "jdbc:$databaseUrl"
            }
            Database.connect(jdbcUrl)
            transaction {
                SchemaUtils.createMissingTablesAndColumns(
                    Users,
                    Decks,
                    DeckCards,
                    Sessions,
                    Games
                )
            }
            return Db()
        }
    }
}

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun providesDb() = Db.connect()
}
