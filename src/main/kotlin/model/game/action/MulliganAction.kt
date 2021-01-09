package model.game.action

import model.game.Game
import model.game.PlayerLabel
import model.game.step.core.MulliganStep

class MulliganAction(
    override val playerLabel: PlayerLabel,
    private val changeIndices: Set<Int>
) : Action {
    override fun validate(game: Game): ActionResult {
        if (changeIndices.count() !in (0..3)) {
            return invalidAction("Too many or too few cards selected for change")
        }
        val player = game.players.getValue(playerLabel)
        if (player.mulliganed) {
            return invalidAction("this player already performed their mulligan")
        }
        return ValidAction(
            MulliganStep(playerLabel, changeIndices)
        )
    }
}
