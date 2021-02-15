import { Action, CreatureSide, GameMode, GameState } from './context';
import { CreaturePosition } from './types';

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
    dispatch({ type: 'command_sent' });
    fetch(new Request(`/api/games/${state.game.gameId}`, {
      method: 'PUT',
      body: JSON.stringify({
        move: {
          from: state.selectedCreature?.position,
          to: position,
        },
      }),
    }))
      .then((response) => response.json())
      .then((body) => dispatch({
        type: 'command_response',
        newGameState: body
      }));
    break;
  }
  case GameMode.ATTACKING: {
    if (side !== 'opponent') {
      break;
    }
    dispatch({ type: 'command_sent' });
    fetch(new Request(`/api/games/${state.game.gameId}`, {
      method: 'PUT',
      body: JSON.stringify({
        attack: {
          attackPosition: state.selectedCreature?.position,
          targetPosition: position,
        },
      }),
    }))
      .then((response) => response.json())
      .then((body) => dispatch({
        type: 'command_response',
        newGameState: body
      }));
    break;
  }
  case GameMode.SUMMONING: {
    if (side !== 'self') {
      break;
    }
    dispatch({ type: 'command_sent' });
    fetch(new Request(`/api/games/${state.game.gameId}`, {
      method: 'PUT',
      body: JSON.stringify({
        summon: {
          handIndex: state.selectedCard?.handPosition,
          position,
        },
      }),
    }))
      .then((response) => response.json())
      .then((body) => dispatch({
        type: 'command_response',
        newGameState: body
      }));
  }
  }
}

export function endTurn(
  state: GameState,
  dispatch: React.Dispatch<Action>
): void {
  dispatch({ type: 'command_sent' });
  fetch(new Request(`/api/games/${state.game.gameId}`, {
    method: 'PUT',
    body: JSON.stringify({
      end: true,
    }),
  }))
    .then((response) => response.json())
    .then((body) => dispatch({
      type: 'command_response',
      newGameState: body,
    }));
}
