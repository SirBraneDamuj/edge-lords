package model.game

import kotlinx.serialization.Serializable
import model.card.Decks
import model.game.action.EndTurnAction
import util.whenIts

@Serializable
data class Game(
    val players: Map<PlayerLabel, Player>,
    var turn: Int,
    var winner: Winner = Winner.NONE
) {
    val activePlayer: Player
        get() = players[activePlayerLabel] ?: error("this isn't possible")
    val inactivePlayer: Player
        get() = players[activePlayerLabel.other] ?: error("this isn't possible")
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

object Games {
    fun createFakeGame(): Game {
        val deck1 = Decks.loadDeck("/decks/deck1.yml")
        val deck2 = Decks.loadDeck("/decks/deck2.yml")
        return Game(
            players = mapOf(
                PlayerLabel.FIRST to Players.createPlayerForDeck(
                    name = "Rean",
                    label = PlayerLabel.FIRST,
                    deck = deck1
                ),
                PlayerLabel.SECOND to Players.createPlayerForDeck(
                    name = "Juna",
                    label = PlayerLabel.SECOND,
                    deck = deck2
                )
            ),
            turn = 1
        )
    }
}
