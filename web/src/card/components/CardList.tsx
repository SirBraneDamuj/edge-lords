import React from 'react';
import { Card } from '../types';
import CardDetail from './CardDetail';

interface Props {
  cards: Array<Card>
  onCardSelect?: (c: Card) => void
  selectable?: boolean
  wrap?: boolean
}

export default function CardList({
  cards,
  onCardSelect,
  wrap = true,
  selectable = false,
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
      <div onClick={() => !!onCardSelect && onCardSelect(card)} key={index} style={styles.card}>
        <CardDetail card={card} />
      </div>
    );
  });
  return (
    <div style={styles.list}>
      {cardDetails}
    </div>
  );
}
