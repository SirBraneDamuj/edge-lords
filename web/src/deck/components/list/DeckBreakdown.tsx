import React from 'react';
import NatialsBreakdown from './NatialsBreakdown';
import SpellsBreakdown from './SpellsBreakdown';

interface Props {
  cards: Record<string, number>
}

export default function DeckBreakdown({
  cards
}: Props): JSX.Element {
  const styles = {
    display: 'flex',
    flexDirection: 'row' as const,
    justifyContent: 'space-around',
    padding: '0.5rem',
  };
  return (
    <div style={styles}>
      <NatialsBreakdown cards={cards} />
      <SpellsBreakdown cards={cards} />
    </div>
  );
}
