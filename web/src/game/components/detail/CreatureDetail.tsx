import React from 'react';
import CardHeader from '../../../card/components/CardHeader';
import CardMid from '../../../card/components/CardMid';
import { CardType } from '../../../card/types';
import { Creature } from '../../types';

interface Props {
  creature: Creature
}

export default function CreatureDetail({
  creature: {
    className,
    card: {
      cardName,
      manaCost,
    },
    attack,
    hp,
    range,
    speed,
    element,
  }
}: Props): JSX.Element {
  const cardType = className.split('.').pop() as CardType;
  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'column' as const,
      justifyContent: 'space-around',
      width: 200,
      height: 100,
      border: '1px solid black',
      margin: 2,
      padding: 5,
    },
    header: {
      display: 'flex',
      flexDirection: 'row' as const,
    }
  };
  return (
    <div style={styles.container}>
      <CardHeader
        element={element??undefined}
        range={range}
        speed={speed}
        name={cardName}
        manaCost={manaCost}
      />
      <CardMid
        attack={attack}
        hp={hp}
        cardType={cardType}
      />
    </div>
  );
}
