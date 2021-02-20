package model.game.step.skill.natials

import model.game.Game
import model.game.Row
import model.game.step.GameStep
import model.game.step.effects.DamageRowOfEnemiesStep
import util.toSingletonList

const val GIABRO_ROW_DAMAGE = 2

class GiaBroStep(
    private val row: Row
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageRowOfEnemiesStep(game.inactivePlayer.playerLabel, row, GIABRO_ROW_DAMAGE).toSingletonList()
    }
}
