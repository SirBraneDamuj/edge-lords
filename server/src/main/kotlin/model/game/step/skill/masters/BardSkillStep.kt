package model.game.step.skill.masters

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.SealCreatureStep
import util.toSingletonList

const val BARD_SEAL_AMOUNT = 1

class BardSkillStep(
    private val targetPosition: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return SealCreatureStep(
            game.inactivePlayer.playerLabel,
            targetPosition,
            BARD_SEAL_AMOUNT
        ).toSingletonList()
    }
}