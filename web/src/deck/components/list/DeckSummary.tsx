import React from 'react';
import { Deck } from '../../types';
import NatialsBreakdown from './NatialsBreakdown';
import SpellsBreakdown from './SpellsBreakdown';
import './DeckSummary.css';

interface Props {
  deck: Deck
}

export default function DeckSummary({
  deck
}: Props): JSX.Element {
  return (
    <div className={'deck-summary'}>
      <div className={'deck-description'}>
        <div>Name: {deck.name}</div>
        <div>Master: {deck.master}</div>
      </div>
      <div className={'deck-breakdown'}>
        <NatialsBreakdown deck={deck} />
        <SpellsBreakdown deck={deck} />
      </div>
    </div>
  );
}
