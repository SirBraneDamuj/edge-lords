package model.game.action

import model.Cards
import model.card.Card
import model.card.MasterCard
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.Row
import model.game.step.core.CreatureSkillStep
import model.game.step.skill.BaMadoStep
import model.game.step.skill.DarmaSkillStep
import model.game.step.skill.DullmdallaStep
import model.game.step.skill.FifeNallStep
import model.game.step.skill.GiaBroStep
import model.game.step.skill.GueneFossStep
import model.game.step.skill.KyriaBellStep
import model.game.step.skill.MarmeStep
import model.game.step.skill.NeptjunoStep
import model.game.step.skill.PelittStep
import model.game.step.skill.RegnaCroxeStep
import model.game.step.skill.TentarchStep
import model.game.step.skill.ZamilpenStep
import model.game.step.skill.ZenosbleadStep

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
            "Gia-Bro" -> GiaBroStep(
                rowTarget() ?: return targetError()
            )
            "Ba-Mado" -> BaMadoStep(
                singleEnemyTarget(game) ?: return targetError()
            )
            "Marme" -> MarmeStep(
                singleAllyTarget(game) ?: return targetError()
            )
            "Zamilpen" -> ZamilpenStep(
                singleEnemyTarget(game) ?: return targetError()
            )
            "Neptjuno" -> NeptjunoStep(
                singleEnemyTarget(game) ?: return targetError()
            )
            "Tentarch" -> TentarchStep()
            "Dullmdalla" -> DullmdallaStep(
                rowTarget() ?: return targetError()
            )
            "Zenosblead" -> ZenosbleadStep(
                singleEnemyTarget(game) ?: return targetError()
            )
            "Pelitt" -> PelittStep(
                singleAllyTarget(game) ?: return targetError()
            )
            "Guene-Foss" -> GueneFossStep(
                singleEnemyTarget(game) ?: return targetError()
            )
            "Kyria-Bell" -> KyriaBellStep(
                rowTarget() ?: return targetError()
            )
            "Fifenall" -> FifeNallStep(
                singleAllyTarget(game) ?: return targetError()
            )
            "Regna-Croxe" -> RegnaCroxeStep(
                rowTarget() ?: return targetError()
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
