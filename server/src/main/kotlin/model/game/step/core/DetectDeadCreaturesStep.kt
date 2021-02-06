package model.game.step.core

import model.game.Game
import model.game.step.GameStep

class DetectDeadCreaturesStep : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return mutableListOf<GameStep>().apply {
            game.players.values.forEach { player ->
                player.creatures.forEach { creature ->
                    if (creature.hp <= 0) {
                        this.add(DestroyCreatureStep(player.playerLabel, creature.position))
                    }
                }
            }
        }
    }
}
