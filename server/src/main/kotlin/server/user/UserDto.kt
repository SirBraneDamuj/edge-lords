package server.user

import server.deck.DeckDto
import java.util.*

data class CreateUserRequest(
    val name: String,
    val password: String
)

data class UserDto(
    val id: UUID,
    val name: String,
    val decks: List<DeckDto>
) {
    companion object {
        fun fromUser(user: User) =
            UserDto(
                id = user.id.value,
                name = user.name,
                decks = user.decks.map(DeckDto.Companion::fromDeck)
            )
    }
}
