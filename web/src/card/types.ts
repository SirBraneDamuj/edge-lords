export interface Card {
  name: string;
  manaCost: number;
  attack: number;
  hp: number;
  range: AttackRange;
  speed: ActionSpeed;
  element?: Element;
  skillText: string;
  skillManaCost?: number;
  abilityText: string;
  effectText: string;
  cardType: CardType;
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
