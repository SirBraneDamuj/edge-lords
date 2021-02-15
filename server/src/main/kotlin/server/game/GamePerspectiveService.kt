package server.game

import javax.inject.Inject

class GamePerspectiveService @Inject constructor() {
    fun buildPerspective(
        gameId: Int,
        playerId: Int,
        gameState: model.game.Game,
    ): GamePerspective {
        val me = gameState.players.values.single { it.id == playerId.toString() }
        val opponent = gameState.players.values.single { it.id != playerId.toString() }
        return GamePerspective(
            gameId = gameId,
            opponent = OpponentPerspective(
                name = opponent.name,
                handCount = opponent.hand.size,
                deckCount = opponent.deck.size,
                mana = opponent.mana,
                maxMana = opponent.maxMana,
                creatures = opponent.creatures,
                magicCrystals = opponent.magicCrystals.toList(),
                mulliganed = opponent.mulliganed,
                activePlayer = gameState.activePlayerLabel == opponent.playerLabel
            ),
            self = SelfPerspective(
                name = me.name,
                deckCount = me.deck.size,
                hand = me.hand,
                mana = me.mana,
                maxMana = me.maxMana,
                creatures = me.creatures,
                magicCrystals = me.magicCrystals.toList(),
                mulliganed = me.mulliganed,
                activePlayer = gameState.activePlayerLabel == me.playerLabel
            )
        )
    }
}