import React from 'react';
import { CardType } from '../types';
import CardCombatStat from './CardCombatStat';

interface Props {
  attack: number;
  hp: number;
  cardType: CardType;
}

export default function CardMid({
  attack,
  hp,
  cardType
}: Props): JSX.Element {
  return (
    <div className={'card-mid'}>
      <CardCombatStat
        stat={attack}
        label={'ATK'}
      />
      <div>{cardType}</div>
      <CardCombatStat
        stat={hp}
        label={'HP'}
      />
    </div>
  );
}