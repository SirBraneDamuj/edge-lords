import { ActionSpeed, AttackRange, Element } from '../card/types';

export enum GameProgressState {
  WON,
  LOST,
  IN_PROGRESS,
}

export interface GameListEntry {
  id: string
  deckId: string
  deckName: string
  opponentName: string
  state: GameProgressState
}

export interface GamePerspective {
  id: string
  self: SelfPerspective
  opponent: OpponentPerspective
}

export interface SelfPerspective {
  name: string
  deckCount: number
  hand: GameCard[]
  mana: number
  maxMana: number
  creatures: Creature[]
}

export interface OpponentPerspective {
  name: string
  deckCount: number
  handCount: number
  mana: number
  maxMana: number
  creatures: Creature[]
}

export enum CreaturePosition {
  FRONT_ONE = 'FRONT_ONE',
  FRONT_TWO = 'FRONT_TWO',
  FRONT_THREE = 'FRONT_THREE',
  FRONT_FOUR = 'FRONT_FOUR',
  BACK_ONE = 'BACK_ONE',
  BACK_TWO = 'BACK_TWO',
  BACK_THREE = 'BACK_THREE',
}

export enum ActivationState {
  NOT_READY = 'NOT_READY',
  READY = 'READY',
  MOVED = 'MOVED',
  READY_AGAIN = 'READY_AGAIN',
  ACTIVATED = 'ACTIVATED',
}

export interface Creature {
  className: string
  id: string
  card: GameCard
  position: CreaturePosition
  activationState: ActivationState
  attack: number
  hp: number
  maxHp: number
  range: AttackRange
  speed: ActionSpeed
  element: Element | null
}

export interface GameCard {
  className: string
  id: string
  cardName: string
  manaCost: number
}
