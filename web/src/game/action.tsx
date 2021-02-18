import { Card, EffectTargetingMode } from '../card/types';
import { Action, CreatureSide, GameMode, GameState } from './context';
import { Creature, CreaturePosition, GameCard } from './types';

function targetTokensForTargetMode(
  position: CreaturePosition,
  side: CreatureSide,
  creature: Creature | null,
  targetingMode: EffectTargetingMode
) {
  switch (targetingMode) {
  // TODO: enforce a non-empty cell
  case EffectTargetingMode.SINGLE_ALLY: {
    if (side !== 'self') return null;
    if (creature === null) return null;
    return [position];
  }
  case EffectTargetingMode.SINGLE_ENEMY: {
    if (side !== 'opponent') return null;
    if (creature === null) return null;
    return [position];
  }
  case EffectTargetingMode.EMPTY_ALLY: {
    if (side !== 'self') return null;
    if (creature !== null) return null;
    return [position];
  }
  case EffectTargetingMode.ROW_ALLY: {
    if (side !== 'self') return null;
    if (position.startsWith('FRONT')) {
      return ['FRONT'];
    } else {
      return ['BACK'];
    }
  }
  case EffectTargetingMode.ROW_ENEMY: {
    if (side !== 'opponent') return null;
    if (position.startsWith('FRONT')) {
      return ['FRONT'];
    } else {
      return ['BACK'];
    }
  }
  case EffectTargetingMode.ALL_ALLY: {
    if (side !== 'self') return null;
    return [];
  }
  case EffectTargetingMode.ALL_ENEMY: {
    if (side !== 'opponent') return null;
    return [];
  }
  case EffectTargetingMode.ALL: {
    return [];
  }
  }
}

export function selectGridCell(
  side: CreatureSide,
  position: CreaturePosition,
  creature: Creature | null,
  card: Card | null,
  state: GameState,
  dispatch: React.Dispatch<Action>,
): void {
  switch (state.mode) {
  case GameMode.VIEW: {
    if (creature !== null && card !== null) {
      dispatch({
        type: 'select_creature',
        side,
        position,
        creature,
        card,
      });
    }
    break;
  }
  case GameMode.MOVING: {
    if (side !== 'self') {
      break;
    }
    sendCommand(
      state.game.gameId,
      JSON.stringify({
        move: {
          from: state.selectedCreature?.position,
          to: position,
        },
      }),
      dispatch,
    );
    break;
  }
  case GameMode.ATTACKING: {
    if (side !== 'opponent') {
      break;
    }
    sendCommand(
      state.game.gameId,
      JSON.stringify({
        attack: {
          attackPosition: state.selectedCreature?.position,
          targetPosition: position,
        },
      }),
      dispatch,
    );
    break;
  }
  case GameMode.SUMMONING: {
    if (side !== 'self') {
      break;
    }
    sendCommand(
      state.game.gameId,
      JSON.stringify({
        summon: {
          handIndex: state.selectedCard?.handPosition,
          position,
        },
      }),
      dispatch,
    );
    break;
  }
  // TODO: make this action understand a side too
  case GameMode.PLAYING_SPELL: {
    const { selectedCard } = state;
    if (selectedCard) {
      const { handPosition: spellHandPosition, card: spellBeingCast } = selectedCard;
      const targetTokens = targetTokensForTargetMode( position, side, creature, spellBeingCast.targetingMode,);
      if (targetTokens !== null) {
        sendCommand(
          state.game.gameId,
          JSON.stringify({
            spell: {
              handIndex: spellHandPosition,
              targetTokens,
            }
          }),
          dispatch,
        );
      }
    }
    break;
  }
  case GameMode.USING_SKILL: {
    const { selectedCreature } = state;
    if (selectedCreature) {
      const { position: selectedPosition, side: selectedSide, creature: creatureUsingSkill, card } = selectedCreature;
      if (selectedSide !== 'self') {
        console.log('oh dear I\'ve somehow started using an enemy\'s skill.');
      }
      const targetTokens = targetTokensForTargetMode(position, side, creature, card?.targetingMode);
      if (targetTokens !== null) {
        sendCommand(
          state.game.gameId,
          JSON.stringify({
            skill: {
              position: selectedPosition,
              targetTokens,
            }
          }),
          dispatch,
        );
      }
    }
  }
  }
}

export function selectHandCard(
  handPosition: number,
  gameCard: GameCard,
  card: Card,
  state: GameState,
  dispatch: React.Dispatch<Action>
): void {
  switch(state.mode) {
  case GameMode.MULLIGAN: {
    dispatch({
      type: 'select_mulligan_card',
      handPosition,
    });
    break;
  }
  case GameMode.PLAYING_SPELL: {
    const { selectedCard } = state;
    if (selectedCard) {
      const { handPosition: spellHandPosition, card: spellBeingCast } = selectedCard;
      if (spellBeingCast.targetingMode === EffectTargetingMode.HAND_SELF) {
        sendCommand(
          state.game.gameId,
          JSON.stringify({
            spell: {
              handIndex: spellHandPosition,
              targetTokens: [handPosition],
            }
          }),
          dispatch,
        );
      }
    }
    break;
  }
  default: {
    dispatch({
      type: 'select_hand_card',
      handPosition,
      gameCard,
      card,
    });
    break;
  }
  }
}

export function endTurn(
  state: GameState,
  dispatch: React.Dispatch<Action>
): void {
  sendCommand(
    state.game.gameId,
    JSON.stringify({
      end: true,
    }),
    dispatch,
  );
}

export function submitMulligans(
  state: GameState,
  dispatch: React.Dispatch<Action>
): void {
  sendCommand(
    state.game.gameId,
    JSON.stringify({
      mulligan: {
        handIndices: state.mulliganCards,
      },
    }),
    dispatch,
  );
}

function sendCommand(
  gameId: string,
  actionBody: string,
  dispatch: React.Dispatch<Action>,
): void {
  dispatch({ type: 'command_sent' });
  fetch(new Request(`/api/games/${gameId}`, {
    method: 'PUT',
    body: actionBody,
  }))
    .then((response) => response.json())
    .then((body) => dispatch({
      type: 'command_response',
      newGameState: body,
    }));
}
