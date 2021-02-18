export interface Card {
  name: string
  manaCost: number
  attack: number
  hp: number
  range: AttackRange
  speed: ActionSpeed
  element?: Element
  skillText: string
  skillManaCost?: number
  abilityText: string
  effectText: string
  cardType: CardType
  targetingMode: EffectTargetingMode
}

export enum AttackRange {
  RANGED = 'RANGED',
  MELEE = 'MELEE',
}

export enum ActionSpeed {
  NORMAL = 'NORMAL',
  FAST = 'FAST',
}

export enum Element {
  FIRE = 'FIRE',
  HEAVEN = 'HEAVEN',
  EARTH = 'EARTH',
  WATER = 'WATER',
}

export enum CardType {
  NATIAL = 'NATIAL',
  MASTER = 'MASTER',
  SPELL = 'SPELL',
}

export enum EffectTargetingMode {
  NONE = 'NONE',
  DECK_SELF = 'DECK_SELF',
  GRAVEYARD_SELF = 'GRAVEYARD_SELF',
  EMPTY_ALLY = 'EMPTY_ALLY',
  HAND_ENEMY = 'HAND_ENEMY',
  HAND_SELF = 'HAND_SELF',
  SINGLE_ENEMY = 'SINGLE_ENEMY',
  SINGLE_ALLY = 'SINGLE_ALLY',
  ROW_ENEMY = 'ROW_ENEMY',
  ROW_ALLY = 'ROW_ALLY',
  ALL_ENEMY = 'ALL_ENEMY',
  ALL_ALLY = 'ALL_ALLY',
  ALL = 'ALL',
}

export const TargetingModePrompts = {
  [EffectTargetingMode.NONE]: 'You shouldn\'t have been able to cast this... refresh the page I guess.',
  [EffectTargetingMode.DECK_SELF]: 'Draw a card.',
  [EffectTargetingMode.GRAVEYARD_SELF]: 'A random card will be selected from your discard.',
  [EffectTargetingMode.EMPTY_ALLY]: 'Select an empty ally space.',
  [EffectTargetingMode.HAND_ENEMY]: 'A random enemy card will be chosen.',
  [EffectTargetingMode.HAND_SELF]: 'Select a card in your hand.',
  [EffectTargetingMode.SINGLE_ENEMY]: 'Select an enemy creature.',
  [EffectTargetingMode.SINGLE_ALLY]: 'Select an ally creature.',
  [EffectTargetingMode.ROW_ENEMY]: 'Select an enemy row.',
  [EffectTargetingMode.ROW_ALLY]: 'Select an ally row.',
  [EffectTargetingMode.ALL_ENEMY]: 'This will affect all enemy creatures.',
  [EffectTargetingMode.ALL_ALLY]: 'This will affect all ally creatures.',
  [EffectTargetingMode.ALL]: 'This will affect all creatures.',
};

/*
"Tarbyss": {
  "name": "Tarbyss",
  "manaCost": 5,
  "attack": 3,
  "hp": 3,
  "range": "RANGED",
  "speed": "NORMAL",
  "element": "WATER",
  "skillText": "",
  "abilityText": "Draw a card when Tarbyss is destroyed",
  "cardType": "NATIAL"
}
*/
