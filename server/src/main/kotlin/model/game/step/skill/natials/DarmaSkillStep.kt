package model.game.step.skill.natials

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.HealCreatureStep
import util.toSingletonList

const val DARMA_RESTORATION = 2

class DarmaSkillStep(
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return HealCreatureStep(
            game.activePlayerLabel,
            position,
            DARMA_RESTORATION
        ).toSingletonList()
    }
}