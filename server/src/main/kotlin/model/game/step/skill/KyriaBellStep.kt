package model.game.step.skill

import model.game.Game
import model.game.Row
import model.game.step.GameStep
import model.game.step.effects.DamageRowOfEnemiesStep
import util.toSingletonList

const val KYRIABELL_DAMAGE = 2

class KyriaBellStep(
    private val row: Row
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageRowOfEnemiesStep(
            targetPlayerLabel = game.inactivePlayer.playerLabel,
            row = row,
            damage = KYRIABELL_DAMAGE
        ).toSingletonList()
    }
}
