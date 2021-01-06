package model.game

import kotlinx.serialization.Serializable
import util.whenIts

@Serializable
data class Game(
    val players: Map<PlayerLabel, Player>,
    var turn: Int,
    var winner: Winner = Winner.NONE
) {
    val activePlayer: Player
        get() = players[activePlayerLabel] ?: error("this isn't possible")
    val activePlayerLabel: PlayerLabel
        get() = turn.whenIts(
            odd = { PlayerLabel.FIRST },
            even = { PlayerLabel.SECOND }
        )
}

enum class Winner {
    FIRST,
    SECOND,
    NONE;

    companion object {
        fun fromPlayerLabel(playerLabel: PlayerLabel) =
            when (playerLabel) {
                PlayerLabel.FIRST -> FIRST
                PlayerLabel.SECOND -> SECOND
            }
    }
}
