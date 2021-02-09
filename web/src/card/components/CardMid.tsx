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
  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'row' as const,
      justifyContent: 'space-around',
      alignItems: 'center'
    },
  };
  return (
    <div style={styles.container}>
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
