package model.game.action

import model.game.*

class SummonAction(
    private val playerLabel: PlayerLabel,
    private val handIndex: Int,
    private val position: Position
) : Action() {
    override fun perform(game: Game): ActionResult {
        val player = game.players.getValue(playerLabel)
        val card = player.hand.getOrNull(handIndex)
            ?: return invalidAction("Don't know what card $handIndex is")
        val natialCard = when (card) {
            is GameNatialCard -> card
            else -> return invalidAction("Card at $handIndex is not a Natial")
        }
        if (player.mana < card.manaCost) {
            return invalidAction("Not enough mana")
        }
        if (player.creatureAtPosition(position) != null) {
            return invalidAction("There is already a Natial at $position")
        }
        val magicCrystal = player.magicCrystals.contains(position)
        val natial = Natials.summonFromCardToPosition(natialCard, position, magicCrystal)
        player.creatures += natial
        player.mana -= natial.card.manaCost
        player.hand -= natial.card
        player.magicCrystals -= natial.position
        return ValidAction(game)
    }
}
