import React from 'react';
import { Deck } from '../../types';
import './DeckSummary.css';
import DeckBreakdown from './DeckBreakdown';

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
      <DeckBreakdown deck={deck} />
    </div>
  );
}
