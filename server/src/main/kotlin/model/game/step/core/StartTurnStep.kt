package model.game.step.core

import model.AUTO_HEALERS
import model.game.Creature
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep
import model.game.step.effects.HealCreatureStep

class StartTurnStep(
    val playerLabel: PlayerLabel
) : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.player(playerLabel)

        return mutableListOf<GameStep>().apply {
            val startOfRound = game.startOfRound
            if (startOfRound != null && startOfRound != 1 && startOfRound % 3 == 0) {
                game.players.values.forEach {
                    val occupiedPositions = it.creatures.map(Creature::position).toSet() + it.magicCrystals
                    val eligiblePositions = Position.allPositions - occupiedPositions
                    if (!eligiblePositions.isEmpty()) {
                        this.add(CreateMagicCrystalStep(it.playerLabel, eligiblePositions.random()))
                    }
                }
            }
            this.add(DrawCardStep(playerLabel))
            this.add(ReadyCreaturesStep(playerLabel, Position.allPositions))
            this.add(IncrementAndRestoreManaStep(playerLabel))
            player.creatures.forEach {
                if (it.card.cardName in AUTO_HEALERS) {
                    this.add(HealCreatureStep(playerLabel, it.position, 1))
                }
            }
        }
    }
}
