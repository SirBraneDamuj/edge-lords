package server.game

import model.game.Creature
import model.game.GameCard

data class StartGameRequest(
    val deckIds: List<Int>
)

data class CreatedGame(
    val id: Int
)

data class GameList(
    val games: List<GameListEntry>
)

enum class GameProgressState {
    WON,
    LOST,
    IN_PROGRESS,
}

data class GameListEntry(
    val id: Int,
    val deckId: Int,
    val deckName: String,
    val opponentName: String,
    val state: GameProgressState
)

data class GameDto(
    val id: Int,
    val deckOneId: Int,
    val deckTwoId: Int,
    val playerOneId: Int,
    val playerOneName: String,
    val playerTwoId: Int,
    val playerTwoName: String,
    val state: String
)

data class OpponentPerspective(
    val name: String,
    val handCount: Int,
    val deckCount: Int,
    val mana: Int,
    val maxMana: Int,
    val creatures: List<Creature>
)

data class SelfPerspective(
    val name: String,
    val deckCount: Int,
    val hand: List<GameCard>,
    val mana: Int,
    val maxMana: Int,
    val creatures: List<Creature>
)

data class GamePerspective(
    val gameId: Int,
    val opponent: OpponentPerspective,
    val self: SelfPerspective
)
