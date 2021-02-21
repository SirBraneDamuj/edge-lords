package model.game.step.skill.masters

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.DamageAllCreaturesStep
import util.toSingletonList

const val TYRANT_DAMAGE = 3

class TyrantSkillStep(
    private val myPosition: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageAllCreaturesStep(
            receiverPlayerLabel = game.inactivePlayer.playerLabel,
            dealerPosition = myPosition,
            damage = TYRANT_DAMAGE
        ).toSingletonList()
    }
}