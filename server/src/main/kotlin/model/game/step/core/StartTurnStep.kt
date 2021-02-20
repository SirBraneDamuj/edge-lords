package model.game.step.core

import model.AUTO_HEALERS
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.HealCreatureStep

class StartTurnStep(
    val playerLabel: PlayerLabel
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)

        return mutableListOf<GameStep>().apply {
            this.add(DrawCardStep(playerLabel))
            this.add(ReadyCreaturesStep(playerLabel, Position.allPositions))
            this.add(IncrementAndRestoreManaStep(playerLabel))
            player.creatures.forEach {
                if (it.card.cardName in AUTO_HEALERS) {
                    this.add(HealCreatureStep(playerLabel, it.position, 1))
                }
            }
        }
    }
}
