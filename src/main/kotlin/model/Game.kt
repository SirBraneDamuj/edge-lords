package model

data class Game(
    val playerOne: Player,
    val playerTwo: Player,
    val turnCount: Int,
    val gameState: GameState
)

enum class GameState {
    NOT_STARTED,
    PLAYER_ONE_MULLIGAN,
    PLAYER_TWO_MULLIGAN,
    PLAYER_ONE,
    PLAYER_TWO,
    PLAYER_ONE_WIN,
    PLAYER_TWO_WIN;
}
