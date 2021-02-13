import React, { useReducer } from 'react';
import { CreaturePosition, GamePerspective } from './types';

export enum GameMode {
  VIEW = 'VIEW',
  MOVING = 'MOVING',
  ATTACKING = 'ATTACKING',
  SUMMONING = 'SUMMONING',
  PLAYING_SPELL = 'PLAYING_SPELL',
}

export interface GameState {
  game: GamePerspective
  mode: GameMode 
  selectedCreature: { side: CreatureSide, position: CreaturePosition } | null
  selectedCard: { handPosition: number } | null
}

export type CreatureSide = 'self' | 'opponent';

export type Action =
  | { type: 'select_grid_cell', side: CreatureSide, position: CreaturePosition }
  | { type: 'begin_move' }

const reducer = (state: GameState, action: Action) => {
  switch (action.type) {
  case 'select_grid_cell': {
    const { side, position } = action;
    const creatures = state.game[side].creatures;
    const it = creatures.find((c) => c.position === position);
    if (it) {
      return {
        ...state,
        selectedCreature: { side, position }
      };
    } else {
      return state;
    }
  }
  case 'begin_move': {
    return state;
  }
  default:
    return state;
  }
};

export type GameContextStore = { state: GameState, dispatch: React.Dispatch<Action>}
export const GameContext = React.createContext<GameContextStore | null>(null);

interface Props {
  initialState: GamePerspective
  children: React.ReactNode
}

export function GameContextProvider({
  initialState,
  children,
}: Props): JSX.Element {
  const [state, dispatch] = useReducer(reducer, {
    game: initialState,
    mode: GameMode.VIEW,
    selectedCreature: null,
    selectedCard: null,
  });
  const store = {
    state,
    dispatch,
  };
  return (
    <GameContext.Provider value={store}>
      {children}
    </GameContext.Provider>
  );
}


