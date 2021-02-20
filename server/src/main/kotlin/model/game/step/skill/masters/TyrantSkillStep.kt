package model.game.step.skill.masters

import model.game.Game
import model.game.step.GameStep
import model.game.step.effects.DamageAllCreaturesStep
import util.toSingletonList

const val TYRANT_DAMAGE = 3

class TyrantSkillStep : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DamageAllCreaturesStep(
            game.inactivePlayer.playerLabel,
            TYRANT_DAMAGE
        ).toSingletonList()
    }
}