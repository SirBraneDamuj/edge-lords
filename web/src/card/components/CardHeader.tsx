import React from 'react';
import { Card } from '../types';
import CardIcons from './detail/CardIcons';
import CardManaCost from './detail/CardManaCost';

interface Props {
  card: Card
}

export default function CardHeader({
  card: {
    element,
    range,
    speed,
    name,
    manaCost,
  }
}: Props): JSX.Element {
  const styles = {
    header: {
      display: 'flex',
      flexDirection: 'row' as const,
      justifyContent: 'space-between',
    },
  };
  return (
    <div style={styles.header}>
      <CardIcons
        element={element}
        range={range}
        speed={speed}
      />
      <div className={'card-name'}>{name}</div>
      <CardManaCost manaCost={manaCost} />
    </div>
  );
}
