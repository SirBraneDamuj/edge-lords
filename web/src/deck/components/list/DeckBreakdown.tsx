import React from 'react';
import { Deck } from '../../types';
import NatialsBreakdown from './NatialsBreakdown';
import SpellsBreakdown from './SpellsBreakdown';

interface Props {
  deck: Deck
}

export default function DeckBreakdown({
  deck
}: Props): JSX.Element {
  const styles = {
    display: 'flex',
    flexDirection: 'row' as const,
    justifyContent: 'space-around',
    padding: '0.5rem',
  };
  return (
    <div style={styles}>
      <NatialsBreakdown deck={deck} />
      <SpellsBreakdown deck={deck} />
    </div>
  );
}
