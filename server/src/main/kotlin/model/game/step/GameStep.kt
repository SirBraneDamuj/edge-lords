package model.game.step

import model.game.Game

/*
A GameStep is a discrete change to game state that occurs.
It can output some number of followup steps if it triggers additional state changes.
For example: the end turn step increments the turn counter by 1
and outputs a draw card step, ready creatures step, and a increment/restore mana step
 */
interface GameStep {
    fun perform(game: Game): List<GameStep>
}
