package model.game.step.skill.masters

import model.game.Game
import model.game.Row
import model.game.step.GameStep
import model.game.step.effects.DamageRowOfEnemiesStep
import util.toSingletonList

const val SWORDSMAN_DAMAGE = 1

class SwordsmanSkillStep(
    private val row: Row
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageRowOfEnemiesStep(
            game.inactivePlayer.playerLabel,
            row,
            SWORDSMAN_DAMAGE
        ).toSingletonList()
    }
}