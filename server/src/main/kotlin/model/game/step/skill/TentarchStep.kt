package model.game.step.skill

import model.game.Game
import model.game.Row
import model.game.step.GameStep
import model.game.step.effects.DamageAllCreaturesStep
import model.game.step.effects.DamageRowOfEnemiesStep
import util.toSingletonList

const val TENTARCH_DAMAGE = 4

class TentarchStep : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageAllCreaturesStep(game.inactivePlayer.playerLabel, TENTARCH_DAMAGE).toSingletonList()
    }
}
