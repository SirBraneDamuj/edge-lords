package server.config

import dagger.Module
import dagger.Provides
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import server.deck.Deck
import server.deck.DeckCard
import server.deck.DeckCards
import server.deck.Decks
import server.game.GameDecks
import server.game.Games
import server.session.Sessions
import server.user.User
import server.user.Users
import java.net.URI
import javax.inject.Singleton
import javax.sql.DataSource

class Db private constructor() {
    val user = User
    val deck = Deck
    val deckCard = DeckCard

    companion object {
        fun connect(): Db {
            val databaseUrl = System.getenv("DATABASE_URL")
                ?: "postgresql://edgelords:erebonia@localhost:5432/edgelords"
            val uri = URI(databaseUrl)
            val (username, password) = uri.userInfo.split(":")
            val host = uri.host
            val port = uri.port
            val path = uri.path
            val jdbcUrl = "jdbc:postgresql://$host:$port$path?user=$username&password=$password"
            val flyway = Flyway.configure().dataSource(jdbcUrl, username, password).load()
            flyway.migrate()
            Database.connect(jdbcUrl)
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
