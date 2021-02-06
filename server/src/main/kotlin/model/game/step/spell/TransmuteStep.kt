package model.game.step.spell

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

const val TRANSMUTE_SEAL_COUNT_WITCH = 3
const val TRANSMUTE_SEAL_COUNT = 2

class TransmuteStep(
    val playerLabel: PlayerLabel,
    val target: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val targetPlayer = game.player(playerLabel.other)
        val targetCreature = targetPlayer.creatureAtPosition(target)
            ?: error("There is no creature there... was this action validated?")
        targetCreature.sealCount += when (player.master().card.cardName) {
            "Witch" -> TRANSMUTE_SEAL_COUNT_WITCH
            else -> TRANSMUTE_SEAL_COUNT
        }
        return emptyList()
    }
}
