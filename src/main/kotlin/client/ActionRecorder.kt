package client

import model.game.Game
import model.game.Position
import model.game.action.*
import kotlin.system.exitProcess

class ActionInputException(
    message: String
) : RuntimeException(message)

object ActionRecorder {
    fun recordAction(game: Game): Action {
        val player = game.activePlayer
        val playerLabel = player.playerLabel
        println("It's turn ${game.turn}.")
        println("${player.name}, enter an action (end mulligan move summon attack): ")
        val actionInput = readLine() ?: exitProcess(0) // :)
        val tokens = actionInput.split(" ")
        return when (val actionName = tokens.first()) {
            "end" -> EndTurnAction(playerLabel)
            "mulligan" -> {
                val indices = tokens.drop(1).map(String::toInt)
                val handIndices = indices.toSet()
                if (indices.count() != handIndices.count()) {
                    throw ActionInputException("You tried to mulligan some dupes")
                }
                MulliganAction(playerLabel, indices.toSet())
            }
            "move" -> {
                val from = stringToPosition(tokens[1])
                val to = stringToPosition(tokens[2])
                MoveAction(playerLabel, from, to)
            }
            "summon" -> {
                val handIndex = tokens[1].toInt()
                val position = stringToPosition(tokens[2])
                SummonAction(playerLabel, handIndex, position)
            }
            "attack" -> {
                val attackerPosition = stringToPosition(tokens[1])
                val defenderPosition = stringToPosition(tokens[2])
                AttackAction(playerLabel, attackerPosition, defenderPosition)
            }
            else -> throw ActionInputException("invalid action name $actionName")
        }
    }

    private fun stringToPosition(s: String): Position {
        return when (s[0]) {
            'B' -> {
                when (s[1]) {
                    '1' -> Position.BACK_ONE
                    '2' -> Position.BACK_TWO
                    '3' -> Position.BACK_THREE
                    else -> throw ActionInputException("invalid position")
                }
            }
            'F' -> {
                when (s[1]) {
                    '1' -> Position.FRONT_ONE
                    '2' -> Position.FRONT_TWO
                    '3' -> Position.FRONT_THREE
                    '4' -> Position.FRONT_FOUR
                    else -> throw ActionInputException("invalid position")
                }
            }
            else -> throw ActionInputException("position not valid")
        }
    }
}
