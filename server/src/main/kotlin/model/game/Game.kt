package model.game

import com.fasterxml.jackson.annotation.JsonIgnore
import model.card.Deck
import model.card.SampleDecks
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
        @JsonIgnore get() = turn.whenIts(
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
    fun createGame(
        deck1: Deck,
        deck2: Deck
    ): Game {
        return Game(
            id = UUID.randomUUID().toString(),
            players = mapOf(
                PlayerLabel.FIRST to Players.createPlayerForDeck(
                    name = deck1.playerName,
                    id = deck1.playerId,
                    label = PlayerLabel.FIRST,
                    deck = deck1
                ),
                PlayerLabel.SECOND to Players.createPlayerForDeck(
                    name = deck2.playerName,
                    id = deck2.playerId,
                    label = PlayerLabel.SECOND,
                    deck = deck2
                )
            ),
            turn = 1
        )
    }

    fun createFakeGame(): Game {
        val deck1 = SampleDecks.loadDeck("/decks/deck1.yml")
        val deck2 = SampleDecks.loadDeck("/decks/deck2.yml")
        return createGame(deck1, deck2)
    }
}
