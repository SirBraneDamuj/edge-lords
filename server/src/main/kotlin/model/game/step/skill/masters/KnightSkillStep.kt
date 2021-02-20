package model.game.step.skill.masters

import model.game.Game
import model.game.step.GameStep

const val KNIGHT_ATTACK_INCREASE = 1

class KnightSkillStep : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val player = game.activePlayer
        player.creatures.forEach {
            it.attack += KNIGHT_ATTACK_INCREASE
        }
        return emptyList()
    }
}