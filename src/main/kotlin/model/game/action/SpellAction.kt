package model.game.action

import model.Cards
import model.game.*
import model.game.step.core.PlaySpellStep
import model.game.step.spell.*

private const val TARGET_ERROR = "Something was wrong with the target"

private fun targetError() = invalidAction(TARGET_ERROR)

class SpellAction(
    override val playerLabel: PlayerLabel,
    val handIndex: Int,
    val targetTokens: List<String>
) : Action {
    override fun validate(game: Game): ActionResult {
        val player = game.player(playerLabel)
        val gameCard = player.hand.getOrNull(handIndex)
            ?: return invalidAction("That hand index is invalid")
        val gameSpellCard = gameCard as? GameSpellCard
            ?: return invalidAction("${gameCard.cardName} isn't a spell card")
        val card = Cards.getSpellByName(gameSpellCard.cardName)
            ?: error("Somehow, you have a card in your hand that isn't real!")

        /*
         TODO: can this be configuration instead of code?
         all spell steps just need the player label and their target
         */
        val spellStep = when (card.name) {
            "Blaze" -> BlazeStep(
                playerLabel,
                singleEnemyTarget(game) ?: return targetError()
            )
            "Disaster" -> DisasterStep(
                playerLabel,
                rowTarget() ?: return targetError()
            )
            "Expel" -> ExpelStep(
                playerLabel,
                singleEnemyTarget(game) ?: return targetError()
            )
            "Magic Crystal" -> MagicCrystalStep(
                playerLabel,
                singleAllyTarget(game) ?: return targetError()
            )
            "Medic" -> MedicStep(
                playerLabel,
                singleAllyTarget(game) ?: return targetError()
            )
            "Reduce" -> ReduceStep(
                playerLabel,
                handTarget() ?: return targetError()
            )
            "Transmute" -> TransmuteStep(
                playerLabel,
                singleEnemyTarget(game) ?: return targetError()
            )
            "Uptide" -> targetTokens
                .takeIf { it.isEmpty() }
                ?.let { UptideStep() }
                ?: return targetError()
            "Vanish" -> VanishStep(
                playerLabel,
                singleEnemyTarget(game) ?: return targetError()
            )
            "Wall" -> WallStep(
                playerLabel,
                singleAllyTarget(game) ?: return targetError()
            )
            else -> error("I don't know how to use this spell.")
        }
        return ValidAction(
            PlaySpellStep(
                playerLabel, handIndex, spellStep
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
