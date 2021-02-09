import React from 'react';
import { Card } from '../types';
import CardHeader from './CardHeader';
import CardMid from './CardMid';
import CardFooter from './CardFooter';

interface Props {
  card: Card;
}

export default function CardDetail({ card }: Props): JSX.Element {
  const styles = {
    display: 'flex',
    flexDirection: 'column' as const,
    border: '1px black solid',
    width: 300,
    padding: '1rem',
  };
  return (
    <div style={styles}>
      <CardHeader card={card} />
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
