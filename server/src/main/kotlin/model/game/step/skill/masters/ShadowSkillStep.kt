package model.game.step.skill.masters

import model.game.Game
import model.game.GameNatialCard
import model.game.step.GameStep

class ShadowSkillStep : GameStep {
    override fun perform(game: Game): List<GameStep> {
        val targetPlayer = game.inactivePlayer
        val destroyedCard = targetPlayer.hand.random()
        targetPlayer.hand = targetPlayer.hand - destroyedCard
        if (destroyedCard is GameNatialCard) {
            targetPlayer.discard = targetPlayer.discard + destroyedCard
        }
        return emptyList()
    }
}