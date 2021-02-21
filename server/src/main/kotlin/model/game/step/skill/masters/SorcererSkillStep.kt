package model.game.step.skill.masters

import model.game.Game
import model.game.Position
import model.game.step.GameStep
import model.game.step.core.CreateMagicCrystalStep
import util.toSingletonList

class SorcererSkillStep(
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return CreateMagicCrystalStep(
            playerLabel = game.activePlayerLabel,
            position = position
        ).toSingletonList()
    }
}