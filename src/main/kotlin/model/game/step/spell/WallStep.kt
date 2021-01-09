package model.game.step.spell

import model.Element
import model.game.Game
import model.game.PlayerLabel
import model.game.Position
import model.game.step.GameStep

const val WALL_GUARD_COUNT = 1
const val WALL_HP_RESTORATION_EARTH = 3

class WallStep(
   val playerLabel: PlayerLabel,
   val position: Position
) : GameStep {
   override fun perform(game: Game): List<GameStep> {
      val player = game.player(playerLabel)
      val creature = player.creatureAtPosition(position)
         ?: error("there is no creature there... was this action validated?")

      creature.guardCount += WALL_GUARD_COUNT
      if (creature.element == Element.EARTH) {
         creature.heal(WALL_HP_RESTORATION_EARTH)
      }
      return emptyList()
   }
}
