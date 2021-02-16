import { Card, EffectTargetingMode } from '../card/types';
import { Action, CreatureSide, GameMode, GameState } from './context';
import { CreaturePosition, GameCard } from './types';

export function selectGridCell(
  side: CreatureSide,
  position: CreaturePosition,
  state: GameState,
  dispatch: React.Dispatch<Action>,
): void {
  switch (state.mode) {
  case GameMode.VIEW: {
    dispatch({
      type: 'select_creature',
      side,
      position,
    });
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
      let targetTokens: string[] | null = null;
      switch (spellBeingCast.targetingMode) {
      case EffectTargetingMode.SINGLE: {
        targetTokens = [position];
        break;
      }
      case EffectTargetingMode.ROW: {
        if (position.startsWith('FRONT')) {
          targetTokens = ['FRONT'];
        } else {
          targetTokens = ['BACK'];
        }
        break;
      }
      case EffectTargetingMode.ALL: {
        targetTokens = [];
        break;
      }
      }
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
      if (spellBeingCast.targetingMode === EffectTargetingMode.HAND) {
        sendCommand(
          state.game.gameId,
          JSON.stringify({
            spell: {
              handIndex: handPosition,
              targetTokens: [spellHandPosition],
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
