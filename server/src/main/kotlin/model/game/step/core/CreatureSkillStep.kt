package model.game.step.core

import model.game.*
import model.game.step.GameStep
import util.toSingletonList

class CreatureSkillStep(
    private val playerLabel: PlayerLabel,
    private val position: Position,
    private val skillCost: Int,
    private val skillStep: GameStep
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(position)
            ?: error("There was no creature at this position... was this action validated?")
        player.mana -= skillCost
        creature.activationState = creature.activationState.stateAfterActing(creature.card.cardName)
        if (creature is Natial) {
            creature.canUseSkill = false
        }
        return skillStep.toSingletonList()
    }
}