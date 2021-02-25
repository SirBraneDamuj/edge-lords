package model.game.step.effects

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

class DamageAllCreaturesStep(
    private val receiverPlayerLabel: PlayerLabel,
    private val dealerPosition: Position,
    private val damage: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(receiverPlayerLabel)
        return player.creatures.map {
            DealDamageStep(
                dealerPlayerLabel = receiverPlayerLabel.other,
                dealerPosition = dealerPosition,
                receiverPosition = it.position,
                damageAmount = damage
            )
        }
    }
}
