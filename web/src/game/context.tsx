import React, { useReducer } from 'react';
import { Card } from '../card/types';
import { Creature, CreaturePosition, GameCard, GamePerspective } from './types';

export enum GameMode {
  VIEW = 'VIEW',
  MOVING = 'MOVING',
  ATTACKING = 'ATTACKING',
  SUMMONING = 'SUMMONING',
  PLAYING_SPELL = 'PLAYING_SPELL',
  USING_SKILL = 'USING_SKILL',
  MULLIGAN = 'MULLIGAN',
}

export interface GameState {
  game: GamePerspective
  mode: GameMode 
  selectedCreature: { side: CreatureSide, position: CreaturePosition, creature: Creature, card: Card } | null
  selectedCard: { handPosition: number, gameCard: GameCard, card: Card } | null
  mulliganCards: number[],
  loading: boolean
}

export type CreatureSide = 'self' | 'opponent';

export type CommandSentAction = { type: 'command_sent' }
export type CommandResponseAction = { type: 'command_response', newGameState: GamePerspective }

export type SelectCreatureAction = { type: 'select_creature', side: CreatureSide, position: CreaturePosition, creature: Creature, card: Card }
export type BeginMoveAction = { type: 'begin_move' }
export type BeginAttackAction = { type: 'begin_attack' }
export type BeginSkillAction = { type: 'begin_skill' }

export type SelectHandCardAction = { type: 'select_hand_card', handPosition: number, gameCard: GameCard, card: Card }
export type BeginSummonAction = { type: 'begin_summon' }
export type BeginCastAction = { type: 'begin_cast' }

export type SelectMulliganCardAction = { type: 'select_mulligan_card', handPosition: number }

export type Action =
  | SelectCreatureAction
  | BeginMoveAction
  | BeginAttackAction
  | BeginSkillAction
  | SelectHandCardAction
  | BeginSummonAction
  | BeginCastAction
  | CommandSentAction
  | CommandResponseAction
  | SelectMulliganCardAction


const reducer = (state: GameState, action: Action) => {
  switch (action.type) {
  case 'select_creature': {
    const { side, position, creature, card } = action;
    return {
      ...state,
      selectedCard: null,
      selectedCreature: { side, position, creature, card },
    };
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
  case 'begin_skill': {
    return {
      ...state,
      mode: GameMode.USING_SKILL,
    };
  }
  case 'select_hand_card': {
    const { handPosition, gameCard, card } = action;
    return {
      ...state,
      selectedCreature: null,
      selectedCard: { handPosition, gameCard, card }
    };
  }
  case 'select_mulligan_card': {
    const { handPosition } = action;
    const { mulliganCards } = state;
    if (!mulliganCards.includes(handPosition)) {
      return {
        ...state,
        mulliganCards: [
          ...mulliganCards,
          handPosition,
        ],
      };
    }
    return state;
  }
  case 'begin_summon': {
    return {
      ...state,
      mode: GameMode.SUMMONING,
    };
  }
  case 'begin_cast': {
    return {
      ...state,
      mode: GameMode.PLAYING_SPELL,
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
  const mode = (initialState.self.activePlayer && !initialState.self.mulliganed) ?
    GameMode.MULLIGAN :
    GameMode.VIEW;
  const [state, dispatch] = useReducer(reducer, {
    game: initialState,
    mode,
    selectedCreature: null,
    selectedCard: null,
    loading: false,
    mulliganCards: [],
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


