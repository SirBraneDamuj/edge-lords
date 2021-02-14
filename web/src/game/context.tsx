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
  loading: boolean
}

export type CreatureSide = 'self' | 'opponent';

export type CommandSentAction = { type: 'command_sent' }
export type CommandResponseAction = { type: 'command_response', newGameState: GamePerspective }

export type SelectCreatureAction = { type: 'select_creature', side: CreatureSide, position: CreaturePosition }
export type BeginMoveAction = { type: 'begin_move' }
export type BeginAttackAction = { type: 'begin_attack' }

export type Action =
  | SelectCreatureAction
  | BeginMoveAction
  | BeginAttackAction
  | CommandSentAction
  | CommandResponseAction


const reducer = (state: GameState, action: Action) => {
  switch (action.type) {
  case 'select_creature': {
    const { side, position } = action;
    const it = state.game[side].creatures.find((c) => c.position === position);
    if (it) {
      return {
        ...state,
        selectedCreature: { side, position }
      };
    }
    break;
  }
  case 'command_sent': {
    return {
      ...state,
      mode: GameMode.VIEW,
      loading: true,
    };
  }
  case 'command_response': {
    const { newGameState } = action;
    return {
      ...state,
      selectedCard: null,
      selectedCreature: null,
      game: newGameState,
    };
  }
  case 'begin_move': {
    return {
      ...state,
      mode: GameMode.MOVING,
    };
  }
  case 'begin_attack': {
    return {
      ...state,
      mode: GameMode.ATTACKING,
    };
  }
  default:
    return state;
  }
  return state;
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
    loading: false,
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


