package model.game.action

import model.Cards
import model.card.Card
import model.card.MasterCard
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.Row
import model.game.step.core.CreatureSkillStep
import model.game.step.skill.masters.*
import model.game.step.skill.natials.*

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
        if (!creature.activationState.canAct || creature.sealed) {
            return invalidAction("This creature can't act right now.")
        }
        val gameCard = creature.card
        val card: Card = Cards.getMasterByName(gameCard.cardName)
            ?: Cards.getNatialByName(gameCard.cardName)
            ?: error("Somehow you have a creature that isn't real!")
        val skillStep = when (card.name) {
            /* NATIALS */
            "D-Arma" -> DarmaSkillStep(
                singleAllyTarget(game) ?: return targetError()
            )
            "Gia-Bro" -> GiaBroStep(
                myPosition = creaturePosition,
                row = rowTarget() ?: return targetError()
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
                myPosition = creaturePosition,
                position = singleEnemyTarget(game) ?: return targetError()
            )
            "Tentarch" -> TentarchStep(creaturePosition)
            "Dullmdalla" -> DullmdallaStep(
                myPosition = creaturePosition,
                row = rowTarget() ?: return targetError()
            )
            "Zenosblead" -> ZenosbleadStep(
                myPosition = creaturePosition,
                position = singleEnemyTarget(game) ?: return targetError()
            )
            "Pelitt" -> PelittStep(
                singleAllyTarget(game) ?: return targetError()
            )
            "Guene-Foss" -> GueneFossStep(
                myPosition = creaturePosition,
                position = singleEnemyTarget(game) ?: return targetError()
            )
            "Kyria-Bell" -> KyriaBellStep(
                myPosition = creaturePosition,
                row = rowTarget() ?: return targetError()
            )
            "Fifenall" -> FifeNallStep(
                singleAllyTarget(game) ?: return targetError()
            )
            "Regna-Croxe" -> RegnaCroxeStep(
                myPosition = creaturePosition,
                row = rowTarget() ?: return targetError()
            )
            /* MASTERS */
            "Bard" -> BardSkillStep(
                singleEnemyTarget(game) ?: return targetError()
            )
            "Beast" -> BeastSkillStep()
            "Knight" -> KnightSkillStep()
            "Paladin" -> PaladinSkillStep(
                singleEmptyTarget(game) ?: return targetError()
            )
            "Shadow" -> ShadowSkillStep()
            "Sister" -> SisterSkillStep(
                singleAllyTarget(game) ?: return targetError()
            )
            "Sorcerer" -> SorcererSkillStep(
                singleEmptyNoMagicCrystalTarget(game) ?: return targetError()
            )
            "Spirit" -> SpiritSkillStep(
                singleAllyTarget(game) ?: return targetError()
            )
            "Swordsman" -> SwordsmanSkillStep(
                myPosition = creaturePosition,
                row = rowTarget() ?: return targetError()
            )
            "Thief" -> ThiefSkillStep()
            "Tyrant" -> TyrantSkillStep(creaturePosition)
            "Witch" -> WitchSkillStep(
                myPosition = creaturePosition,
                targetPosition = singleEnemyTarget(game) ?: return targetError()
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

    private fun singleEmptyTarget(game: Game) =
        targetTokens
            .singleOrNull()
            ?.let(Position.Companion::stringToPosition)
            ?.takeIf { game.activePlayer.creatureAtPosition(it) == null }

    private fun singleEmptyNoMagicCrystalTarget(game: Game) =
        singleEmptyTarget(game)
            ?.takeIf { game.activePlayer.magicCrystals.contains(it) }

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
