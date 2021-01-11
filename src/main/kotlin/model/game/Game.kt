package model.game

import com.fasterxml.jackson.annotation.JsonIgnore
import model.card.Decks
import util.whenIts
import java.util.*

data class Game(
    val id: String,
    val players: Map<PlayerLabel, Player>,
    var turn: Int,
    var winner: Winner = Winner.NONE
) {
    val activePlayer: Player
        @JsonIgnore get() = players[activePlayerLabel] ?: error("this isn't possible")
    val inactivePlayer: Player
        @JsonIgnore get() = players[activePlayerLabel.other] ?: error("this isn't possible")
    val activePlayerLabel: PlayerLabel
        get() = turn.whenIts(
            odd = { PlayerLabel.FIRST },
            even = { PlayerLabel.SECOND }
        )

    fun player(playerLabel: PlayerLabel) =
        players.getValue(playerLabel)
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
            id = UUID.randomUUID().toString(),
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
