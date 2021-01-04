package model.game

data class Game(
    val playerOne: Player,
    val playerTwo: Player,
    val turn: Int
)
