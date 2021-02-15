import React from 'react';
import { Card } from '../types';
import CardDetail from './CardDetail';

interface Props {
  cards: Array<Card>
  onCardSelect?: (c: Card, i: number) => void
  selectable?: boolean
  wrap?: boolean
  highlightPosition?: number
}

export default function CardList({
  cards,
  onCardSelect,
  wrap = true,
  selectable = false,
  highlightPosition = -1,
}: Props): JSX.Element {
  const styles = {
    list: {
      display: 'flex',
      flexDirection: 'row' as const,
      justifyContent: 'flex-start',
      flexWrap: wrap ? 'wrap' as const : 'nowrap' as const,
    },
    card: {
      cursor: selectable ? 'pointer' : 'arrow',
      margin: '1rem',
    },
  };
  const cardDetails = cards.map((card, index) => {
    return (
      <div onClick={() => !!onCardSelect && onCardSelect(card, index)} key={index} style={styles.card}>
        <CardDetail card={card} highlighted={highlightPosition === index} />
      </div>
    );
  });
  return (
    <div style={styles.list}>
      {cardDetails}
    </div>
  );
}
