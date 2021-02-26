package model.game.step.skill.masters

import model.game.Game
import model.game.GameNatialCard
import model.game.Natials
import model.game.Position
import model.game.step.GameStep
import model.game.step.core.CreatureChangePositionStep
import model.game.step.spell.MagicCrystalStep

// TODO: refactor this to work with summon natial step
class PaladinSkillStep(
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.activePlayer
        if (player.creatureAtPosition(position) != null) {
            error("This skill needs an empty square...")
        }
        val natialCard = player.discard.random() as GameNatialCard
        player.discard = player.discard - natialCard

        val magicCrystal = player.magicCrystals.contains(position)
        val natial = Natials.summonFromCardToPosition(natialCard, position)
        player.creatures += natial
        return mutableListOf<GameStep>().apply {
            if (magicCrystal) this.add(MagicCrystalStep(game.activePlayerLabel, position))
            this.add(CreatureChangePositionStep(
                playerLabel = game.activePlayerLabel,
                fromPosition = null,
                toPosition = position
            ))
        }
    }
}
