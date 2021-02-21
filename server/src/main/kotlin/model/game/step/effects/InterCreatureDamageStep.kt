package model.game.step.effects

import model.ATTACK_GAINERS
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.core.DestroyCreatureStep
import util.toSingletonList

class InterCreatureDamageStep(
    private val dealerPlayerLabel: PlayerLabel,
    private val dealerPosition: Position,
    private val receiverPosition: Position,
    private val damageAmount: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val dealer = game.player(dealerPlayerLabel).creatureAtPosition(dealerPosition)
            ?: error("No creature found")
        val receiver = game.player(dealerPlayerLabel.other).creatureAtPosition(receiverPosition)
            ?: error("no creature found")
        receiver.receiveDamage(damageAmount)
        if (receiver.hp <= 0) {
            val attackGain = ATTACK_GAINERS[dealer.card.cardName]
            if (attackGain != null) {
                dealer.attack += attackGain
            }
            return DestroyCreatureStep(dealerPlayerLabel.other, receiverPosition).toSingletonList()
        } else {
            return emptyList()
        }
    }
}