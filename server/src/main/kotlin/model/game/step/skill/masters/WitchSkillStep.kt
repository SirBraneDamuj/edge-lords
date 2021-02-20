package model.game.step.skill.masters

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.DamageSingleCreatureStep
import util.toSingletonList

const val WITCH_DAMAGE_AMOUNT = 4

class WitchSkillStep(
    private val targetPosition: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageSingleCreatureStep(
            game.inactivePlayer.playerLabel,
            targetPosition,
            WITCH_DAMAGE_AMOUNT
        ).toSingletonList()
    }
}