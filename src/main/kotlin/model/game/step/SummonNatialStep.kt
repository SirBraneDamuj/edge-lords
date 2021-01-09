package model.game.step

import model.game.*

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
        val natial = Natials.summonFromCardToPosition(natialCard, position, magicCrystal)
        player.creatures += natial
        player.mana -= natial.card.manaCost
        return mutableListOf<GameStep>().apply {
            if (magicCrystal) this.add(MagicCrystalStep(playerLabel, position))
            this.add(RemoveCardFromHandStep(playerLabel, handPosition))
        }
    }
}
