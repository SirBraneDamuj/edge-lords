package model.game.step.skill.natials

import model.game.Game
import model.game.Position
import model.game.Row
import model.game.step.GameStep
import model.game.step.effects.DamageRowOfEnemiesStep
import util.toSingletonList

const val KYRIABELL_DAMAGE = 2

class KyriaBellStep(
    private val myPosition: Position,
    private val row: Row
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageRowOfEnemiesStep(
            targetPlayerLabel = game.inactivePlayer.playerLabel,
            row = row,
            dealerPosition = myPosition,
            damage = KYRIABELL_DAMAGE
        ).toSingletonList()
    }
}
