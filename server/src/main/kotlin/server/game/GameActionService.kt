package server.game

import com.fasterxml.jackson.databind.ObjectMapper
import model.game.ActionExecutor
import model.game.action.*
import server.error.InvalidRequestError
import server.error.RecordNotFoundError
import java.util.*
import javax.inject.Inject

class GameActionService @Inject constructor(
    private val gameRepository: GameRepository,
    private val gamePerspectiveService: GamePerspectiveService,
    private val objectMapper: ObjectMapper
) {
    fun performAction(
        gameId: UUID,
        playerId: UUID,
        actionDto: ActionDto
    ): GamePerspective {
        val game = gameRepository.findGame(gameId)
            ?: throw RecordNotFoundError()
        val gameState = objectMapper.readValue(game.state, model.game.Game::class.java)
        if (gameState.activePlayer.id != playerId.toString()) {
            throw InvalidRequestError()
        }
        val action = when {
            actionDto.mulligan != null -> {
                MulliganAction(
                    playerLabel = gameState.activePlayerLabel,
                    changeIndices = actionDto.mulligan.handIndices.toSet()
                )
            }
            actionDto.move != null -> {
                MoveAction(
                    playerLabel = gameState.activePlayerLabel,
                    from = actionDto.move.from,
                    to = actionDto.move.to
                )
            }
            actionDto.summon != null -> {
                SummonAction(
                    playerLabel = gameState.activePlayerLabel,
                    handIndex = actionDto.summon.handIndex,
                    position = actionDto.summon.position
                )
            }
            actionDto.attack != null -> {
                AttackAction(
                    playerLabel = gameState.activePlayerLabel,
                    attackerPosition = actionDto.attack.attackPosition,
                    defenderPosition = actionDto.attack.targetPosition
                )
            }
            actionDto.spell != null -> {
                SpellAction(
                    playerLabel = gameState.activePlayerLabel,
                    handIndex = actionDto.spell.handIndex,
                    targetTokens = actionDto.spell.targetTokens
                )
            }
            actionDto.skill != null -> {
                SkillAction(
                    playerLabel = gameState.activePlayerLabel,
                    creaturePosition = actionDto.skill.position,
                    targetTokens = actionDto.skill.targetTokens
                )
            }
            actionDto.end != null -> {
                EndTurnAction(
                    playerLabel = gameState.activePlayerLabel
                )
            }
            else -> throw InvalidRequestError()
        }
        ActionExecutor.performAction(gameState, action)
        gameRepository.saveGame(gameId, objectMapper.writeValueAsString(gameState))
        return gamePerspectiveService.buildPerspective(
            gameId = gameId,
            playerId = playerId,
            gameState = gameState
        )
    }
}
