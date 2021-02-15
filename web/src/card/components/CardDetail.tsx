import React from 'react';
import { Card } from '../types';
import CardFooter from './CardFooter';
import CardHeader from './CardHeader';
import CardMid from './CardMid';

interface Props {
  card: Card
  highlighted?: boolean
}

export default function CardDetail({
  card,
  highlighted = false,
}: Props): JSX.Element {
  const styles = {
    display: 'flex',
    flexDirection: 'column' as const,
    border: highlighted ? '3px black solid' : '1px black solid',
    width: 300,
    padding: '1rem',
  };
  return (
    <div style={styles}>
      <CardHeader {...card} />
      <CardMid
        attack={card.attack}
        hp={card.hp}
        cardType={card.cardType}
      />
      <CardFooter
        skillText={card.skillText}
        abilityText={card.abilityText}
        effectText={card.effectText}
      />
    </div>
  );
}
