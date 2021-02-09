import React from 'react';
import { Deck } from '../../types';
import './DeckSummary.css';
import  { Link } from 'react-router-dom';
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
        <div><Link to={`/deck/${deck.id}`}>Name: {deck.name}</Link></div>
        <div>Master: {deck.master}</div>
      </div>
      <DeckBreakdown deck={deck} />
    </div>
  );
}
