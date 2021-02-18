package model.game.action

import model.Cards
import model.card.Card
import model.card.MasterCard
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.Row
import model.game.step.core.CreatureSkillStep
import model.game.step.skill.DarmaSkillStep

private const val TARGET_ERROR = "Something was wrong with the target"
private fun targetError() = invalidAction(TARGET_ERROR)

class SkillAction(
    override val playerLabel: PlayerLabel,
    val creaturePosition: Position,
    val targetTokens: List<String>
): Action {
    override fun validate(game: Game): ActionResult {
        val player = game.player(playerLabel)
        val creature = player.creatureAtPosition(creaturePosition)
            ?: return invalidAction("ain't no creature at that position")
        val gameCard = creature.card
        val card: Card = Cards.getMasterByName(gameCard.cardName)
            ?: Cards.getNatialByName(gameCard.cardName)
            ?: error("Somehow you have a creature that isn't real!")
        val skillStep = when (card.name) {
            "D-Arma" -> DarmaSkillStep(
                playerLabel,
                singleAllyTarget(game) ?: return targetError()
            )
            else -> return invalidAction("I don't know how to use this creature's skill")
        }
        return ValidAction(
            CreatureSkillStep(
                playerLabel,
                creaturePosition,
                (card as? MasterCard)?.skillManaCost ?: 0,
                skillStep
            )
        )
    }

    private fun singleEnemyTarget(game: Game) =
        targetTokens
            .singleOrNull()
            ?.let(Position.Companion::stringToPosition)
            ?.takeIf { game.inactivePlayer.creatureAtPosition(it) != null }

    private fun singleAllyTarget(game: Game) =
        targetTokens
            .singleOrNull()
            ?.let(Position.Companion::stringToPosition)
            ?.takeIf { game.activePlayer.creatureAtPosition(it) != null }

    private fun rowTarget() =
        targetTokens
            .singleOrNull()
            ?.toUpperCase()
            ?.let(Row::valueOf)

    private fun handTarget() =
        targetTokens
            .singleOrNull()
            ?.toInt()

    private fun allTarget() =
        targetTokens
            .takeIf { it.isEmpty() }
}