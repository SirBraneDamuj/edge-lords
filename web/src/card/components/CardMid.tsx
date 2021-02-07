import React from 'react';
import { CardType } from '../types';
import CardCombatStat from './detail/CardCombatStat';

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
      {attack && <CardCombatStat
        stat={attack}
        label={'ATK'}
      />}
      <div>{cardType}</div>
      {hp && <CardCombatStat
        stat={hp}
        label={'HP'}
      />}
    </div>
  );
}
