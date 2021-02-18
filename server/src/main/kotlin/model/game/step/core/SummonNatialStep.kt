package model.game.step.core

import model.game.*
import model.game.step.GameStep
import model.game.step.spell.MagicCrystalStep

class SummonNatialStep(
    private val playerLabel: PlayerLabel,
    private val handPosition: Int,
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        val natialCard = when (val card = player.hand[handPosition]) {
            is GameNatialCard -> card
            else -> error("this card isn't a natial... did the action get validated?")
        }
        val magicCrystal = player.magicCrystals.contains(position)
        val natial = Natials.summonFromCardToPosition(natialCard, position)
        player.creatures += natial
        return mutableListOf<GameStep>().apply {
            this.add(PlayCardFromHandStep(playerLabel, handPosition))
            if (magicCrystal) this.add(MagicCrystalStep(playerLabel, position))
        }
    }
}
