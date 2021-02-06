package model.game.step.core

import model.game.ActivationState
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

class ReadyCreaturesStep(
    val playerLabel: PlayerLabel,
    val positions: Set<Position>
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        player.creatures.forEach {
            if (it.position in positions) {
                it.activationState = ActivationState.READY
            }
        }
        return emptyList()
    }
}
