package model.game.step.spell

import model.Element
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.CureSealStep
import model.game.step.effects.HealCreatureStep
import util.toSingletonList

const val MEDIC_HP_RESTORATION = 2
const val MEDIC_HEAVEN_ATTACK_INCREASE = 1

class MedicStep(
    val playerLabel: PlayerLabel,
    val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(position)
            ?: error("no creature found... was this action validated?")
        if (creature.element == Element.HEAVEN) {
            creature.attack += MEDIC_HEAVEN_ATTACK_INCREASE
        }
        return listOf(
            HealCreatureStep(
                playerLabel = playerLabel,
                position = position,
                amount = MEDIC_HP_RESTORATION
            ),
            CureSealStep(
                playerLabel = playerLabel,
                position = position
            )
        )
    }
}
