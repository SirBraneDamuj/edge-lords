package model.game.step.skill.masters

import model.game.Game
import model.game.Position
import model.game.step.GameStep

class SorcererSkillStep(
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        if (game.activePlayer.creatureAtPosition(position) != null) {
            error("This skill requires an active space to work")
        }
        game.activePlayer.magicCrystals = game.activePlayer.magicCrystals + position
        return emptyList()
    }
}