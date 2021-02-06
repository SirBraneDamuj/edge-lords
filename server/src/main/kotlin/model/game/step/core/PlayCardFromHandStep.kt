package model.game.step.core

import model.game.Game
import model.game.PlayerLabel
import model.game.step.GameStep

class PlayCardFromHandStep(
    val playerLabel: PlayerLabel,
    val handIndex: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val card = player.hand[handIndex]
        player.mana -= card.manaCost
        return listOf(RemoveCardFromHandStep(playerLabel, handIndex))
    }
}
