package model.game.step.spell

import model.Element
import model.game.Game
import model.game.step.core.DetectDeadCreaturesStep
import model.game.step.GameStep
import util.toSingletonList

const val UPTIDE_NON_WATER_DAMAGE = 2
const val UPTIDE_WATER_RESTORATION = 1

class UptideStep : GameStep {
    override fun perform(game: Game): List<GameStep> {
        game.players.values.forEach { player ->
            player.creatures.forEach { creature ->
                when (creature.element) {
                    Element.WATER -> {
                        creature.increaseMaxHp(UPTIDE_WATER_RESTORATION)
                        creature.attack += UPTIDE_WATER_RESTORATION
                    }
                    null -> {}
                    else -> {
                        creature.hp -= UPTIDE_NON_WATER_DAMAGE
                    }
                }
            }
        }
        return DetectDeadCreaturesStep().toSingletonList()
    }
}
