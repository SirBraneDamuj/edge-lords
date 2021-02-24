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
    private val dealerPosition: Position?,
    private val receiverPosition: Position,
    private val damageAmount: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val dealerPlayer = game.player(dealerPlayerLabel)
        val dealer = dealerPosition?.let { dealerPlayer.creatureAtPosition(dealerPosition) }
        val receiverPlayer = game.player(dealerPlayerLabel.other)
        val receiver = receiverPlayer.creatureAtPosition(receiverPosition)
            ?: error("no creature found")
        val oldHp = receiver.hp
        receiver.receiveDamage(damageAmount)
        return if (receiver.hp <= 0) {
            if (dealer != null) {
                val attackGain = ATTACK_GAINERS[dealer.card.cardName]
                if (attackGain != null) {
                    dealer.attack += attackGain
                }
            }
            DestroyCreatureStep(dealerPlayerLabel.other, receiverPosition).toSingletonList()
        } else {
            if (receiver.card.cardName == "Spirit" && oldHp > 10 && receiver.hp <= 10) {
                receiverPlayer.creatures.map {
                    ChangeStatsStep(
                        receiverPlayer.playerLabel, it.position, 1, 1
                    )
                }
            } else {
                emptyList()
            }
        }
    }
}
