package model.game.step.spell

import model.Element
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

const val BLAZE_ATTACK_BOOST = 2
const val BLAZE_ATTACK_BOOST_FIRE = 3

class BlazeStep(
    val playerLabel: PlayerLabel,
    val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(position)
            ?: error("There is no creature there... was this action validated?")
        creature.attack += when (creature.element) {
            Element.FIRE -> BLAZE_ATTACK_BOOST_FIRE
            else -> BLAZE_ATTACK_BOOST
        }
        return emptyList()
    }
}
