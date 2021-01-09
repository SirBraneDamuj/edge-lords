package model.game.step.spell

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.core.DetectDeadCreaturesStep
import model.game.step.GameStep
import util.toSingletonList

const val VANISH_DAMAGE = 6
const val VANISH_DAMAGE_PALADIN = 7

class VanishStep(
    val playerLabel: PlayerLabel,
    val target: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val targetPlayer = game.player(playerLabel.other)
        val targetCreature = targetPlayer.creatureAtPosition(target)
            ?: error("No creature found... was this action validated?")
        targetCreature.hp -= when (player.master().card.cardName) {
            "Paladin" -> VANISH_DAMAGE_PALADIN
            else -> VANISH_DAMAGE
        }
        return DetectDeadCreaturesStep().toSingletonList()
    }
}
