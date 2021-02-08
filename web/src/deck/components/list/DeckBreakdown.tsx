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
  return (
    <div className={'deck-breakdown'}>
      <NatialsBreakdown deck={deck} />
      <SpellsBreakdown deck={deck} />
    </div>
  );
}
