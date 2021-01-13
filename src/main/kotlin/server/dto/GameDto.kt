package server.dto

data class StartGameRequest(
    val deckIds: List<Int>
)

data class CreatedGame(
    val id: Int
)

