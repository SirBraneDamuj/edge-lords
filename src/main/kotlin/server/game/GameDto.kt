package server.game

data class StartGameRequest(
    val deckIds: List<Int>
)

data class CreatedGame(
    val id: Int
)

