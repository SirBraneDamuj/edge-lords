package model.game.step.effects

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

class HealCreatureStep(
    private val playerLabel: PlayerLabel,
    private val position: Position,
    private val amount: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val targetCreature = player.creatureAtPosition(position)
            ?: error("no creature found... was this action validated?")
        val previousHp = targetCreature.hp
        targetCreature.heal(amount)
        return if (targetCreature.card.cardName == "Spirit" && previousHp <= 10 && targetCreature.hp > 10) {
            player.creatures
                .filter { it.card.cardName != "Spirit" }
                .map {
                    ChangeStatsStep(
                        playerLabel = playerLabel,
                        position = it.position,
                        attackChange = -1,
                        hpChange = -1
                    )
                }
        } else emptyList()
    }
}
