package model.game.step.core

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

class CreateMagicCrystalStep(
    private val playerLabel: PlayerLabel,
    private val position: Position
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)
        player.magicCrystals = player.magicCrystals + position
        return emptyList()
    }
}