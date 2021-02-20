package model.game.step.skill.masters

import model.game.Game
import model.game.step.GameStep
import model.game.step.core.DrawCardStep
import util.toSingletonList

class ThiefSkillStep : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return DrawCardStep(game.activePlayerLabel).toSingletonList()
    }
}