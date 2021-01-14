package server.user

import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.transactions.transaction
import server.config.Db
import server.deck.Deck
import server.session.Credentials
import javax.inject.Inject

class UserRepository @Inject constructor(
    // TODO: make this a @Component instead of injecting like this
    private val db: Db
) {

    fun findUser(id: Int) = transaction {
        User.findById(id)
            ?.load(User::decks, Deck::cards)
            ?.let(UserDto.Companion::fromUser)
    }

    fun findUserCredentials(name: String) =
        findUserByName(name)
            ?.let { Credentials(it.id.value, it.passwordHash) }

    fun findUserByName(name: String) = transaction {
        User.find { Users.name eq name }
            .singleOrNull()
    }

    fun createUser(
        name: String,
        hashedPassword: String
    ) = transaction {
        User.new {
            this.name = name
            this.passwordHash = hashedPassword
        }
    }

}
