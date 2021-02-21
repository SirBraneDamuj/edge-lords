package model.game.step.skill.natials

import model.game.Game
import model.game.Position
import model.game.Row
import model.game.step.GameStep
import model.game.step.effects.DamageAllCreaturesStep
import model.game.step.effects.DamageRowOfEnemiesStep
import util.toSingletonList

const val TENTARCH_DAMAGE = 4

class TentarchStep(
    private val myPosition: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageAllCreaturesStep(
            receiverPlayerLabel = game.inactivePlayer.playerLabel,
            dealerPosition = myPosition,
            damage = TENTARCH_DAMAGE
        ).toSingletonList()
    }
}
