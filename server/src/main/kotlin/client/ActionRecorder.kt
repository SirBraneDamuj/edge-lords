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
        println("${player.name}, enter an action (end mulligan move summon attack spell): ")
        val actionInput = readLine() ?: exitProcess(0) // :)
        val tokens = actionInput.split(" ")
        return when (val actionName = tokens.first()) {
            "exit" -> exitProcess(0)
            "end" -> EndTurnAction(playerLabel)
            "mulligan" -> {
                val usage = "mulligan [handIndex [handIndex [handIndex]]]"
                if (tokens.count() > 4) throw ActionInputException(usage)
                val indices = tokens.drop(1).map { it.toIntOrNull() ?: throw ActionInputException(usage) }
                val handIndices = indices.toSet()
                if (indices.count() != handIndices.count()) {
                    throw ActionInputException("You tried to mulligan some dupes")
                }
                MulliganAction(playerLabel, indices.toSet())
            }
            "move" -> {
                val usage = "move <from> <to>"
                if (tokens.count() != 3) throw ActionInputException(usage)
                val from = Position.stringToPosition(tokens[1])
                val to = Position.stringToPosition(tokens[2])
                MoveAction(playerLabel, from, to)
            }
            "summon" -> {
                val usage = "summon <handIndex> <position>"
                if (tokens.count() != 3) throw ActionInputException(usage)
                val handIndex = tokens[1].toIntOrNull() ?: throw ActionInputException(usage)
                val position = Position.stringToPosition(tokens[2])
                SummonAction(playerLabel, handIndex, position)
            }
            "attack" -> {
                if (tokens.count() != 3) throw ActionInputException("attack <position1> <position2>")
                val attackerPosition = Position.stringToPosition(tokens[1])
                val defenderPosition = Position.stringToPosition(tokens[2])
                AttackAction(playerLabel, attackerPosition, defenderPosition)
            }
            "spell" -> {
                val usage = "spell <handIndex> [target]"
                if (tokens.count() < 2) throw ActionInputException(usage)
                val handIndex = tokens[1].toIntOrNull() ?: throw ActionInputException(usage)
                val targetTokens = tokens.drop(2)
                SpellAction(playerLabel, handIndex, targetTokens)
            }
            else -> throw ActionInputException("invalid action name $actionName")
        }
    }
}
