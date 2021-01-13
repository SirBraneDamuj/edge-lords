package server.dto

import server.model.Deck
import server.model.User
import java.time.LocalDateTime

data class CreateUserRequest(
    val name: String,
    val password: String
)

data class UserDto(
    val id: Int,
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

data class SessionDto(
    val token: String,
    val expiresAt: LocalDateTime,
    val userId: Int
)

data class DeckDto(
    val id: Int,
    val name: String,
    val master: String,
    val cards: Map<String, Int>
) {
    companion object {
        fun fromDeck(deck: Deck) =
            DeckDto(
                id = deck.id.value,
                name = deck.name,
                master = deck.master,
                cards = deck.cards.map { it.name to it.count }.toMap()
            )
    }
}

data class CreateDeckRequest(
    val name: String,
    val master: String,
    val cards: Map<String, Int>
)
