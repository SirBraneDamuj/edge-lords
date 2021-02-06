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
import javax.inject.Singleton

class Db private constructor() {
    val user = User
    val deck = Deck
    val deckCard = DeckCard

    companion object {
        fun connect(): Db {
            Database.connect("jdbc:sqlite:edge-lords.db")
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
