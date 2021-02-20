package model.game.step.skill.masters

import model.game.ActivationState
import model.game.Game
import model.game.Position
import model.game.step.GameStep

class SpiritSkillStep(
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val creature = game.activePlayer.creatureAtPosition(position)
            ?: error("This creature doesn't exist... was this action validated?")
        creature.activationState = ActivationState.READY_AGAIN
        return emptyList()
    }
}