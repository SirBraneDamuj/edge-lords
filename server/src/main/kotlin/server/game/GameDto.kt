package server.game

import model.game.Creature
import model.game.GameCard
import model.game.Position
import java.util.*

data class StartGameRequest(
    val deckIds: List<UUID>
)

data class CreatedGame(
    val id: UUID
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
    val id: UUID,
    val deckId: UUID,
    val deckName: String,
    val opponentName: String,
    val state: GameProgressState
)

data class GameDto(
    val id: UUID,
    val deckOneId: UUID,
    val deckTwoId: UUID,
    val playerOneId: UUID,
    val playerOneName: String,
    val playerTwoId: UUID,
    val playerTwoName: String,
    val state: String
)

data class OpponentPerspective(
    val name: String,
    val handCount: Int,
    val deckCount: Int,
    val mana: Int,
    val maxMana: Int,
    val creatures: List<Creature>,
    val magicCrystals: List<Position>,
    val mulliganed: Boolean,
    val activePlayer: Boolean
)

data class SelfPerspective(
    val name: String,
    val deckCount: Int,
    val hand: List<GameCard>,
    val mana: Int,
    val maxMana: Int,
    val creatures: List<Creature>,
    val magicCrystals: List<Position>,
    val mulliganed: Boolean,
    val activePlayer: Boolean
)

data class GamePerspective(
    val gameId: UUID,
    val opponent: OpponentPerspective,
    val self: SelfPerspective
)
