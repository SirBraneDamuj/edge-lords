package model.game.step.core

import model.game.Game
import model.game.PlayerLabel
import model.game.step.GameStep

class PlaySpellStep(
    val playerLabel: PlayerLabel,
    val handIndex: Int,
    val spellStep: GameStep
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        return listOf(
            PlayCardFromHandStep(playerLabel, handIndex),
            spellStep
        )
    }
}
