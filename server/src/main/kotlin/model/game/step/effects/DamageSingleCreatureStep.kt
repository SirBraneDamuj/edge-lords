package model.game.step.effects

import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.core.DetectDeadCreaturesStep
import util.toSingletonList

class DamageSingleCreatureStep(
    private val targetPlayerLabel: PlayerLabel,
    private val position: Position,
    private val damage: Int
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val targetPlayer = game.player(targetPlayerLabel)
        val targetCreature = targetPlayer.creatureAtPosition(position)
            ?: error("no creature found... was this action validated?")
        targetCreature.receiveDamage(damage)
        return DetectDeadCreaturesStep().toSingletonList()
    }
}