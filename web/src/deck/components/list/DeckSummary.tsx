import React from 'react';
import { Deck } from '../../types';
import  { Link } from 'react-router-dom';
import DeckBreakdown from './DeckBreakdown';

interface Props {
  deck: Deck
}

export default function DeckSummary({
  deck
}: Props): JSX.Element {
  const styles = {
    summary: {
      display: 'flex',
      flexDirection: 'column' as const,
      border: '1px black solid',
    },
    description: {
      display: 'flex',
      flexDirection: 'row' as const,
      justifyContent: 'space-around',
      borderBottom: '1px black solid',
      padding: '0.5rem',
    }
  };
  return (
    <div style={styles.summary}>
      <div style={styles.description}>
        <div><Link to={`/decks/${deck.id}`}>Name: {deck.name}</Link></div>
        <div>Master: {deck.master}</div>
      </div>
      <DeckBreakdown cards={deck.cards} />
    </div>
  );
}
