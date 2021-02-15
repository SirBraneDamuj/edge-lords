import React from 'react';
import { Link } from 'react-router-dom';
import { Deck } from '../../types';
import DeckBreakdown from './DeckBreakdown';
import { DeckListMode } from './DeckList';

interface Props {
  deck: Deck,
  mode: DeckListMode,
}

export default function DeckSummary({
  deck,
  mode,
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
    },
  };
  function renderDeckName(): JSX.Element | null {
    const name = `Name: ${deck.name}`;
    switch (mode) {
    case DeckListMode.DRILL_DOWN: {
      return <Link to={`/decks/${deck.id}`}>{name}</Link>;
    }
    case DeckListMode.SELECTION: {
      return <p>{name}</p>;
    }
    default:
      return null;
    }
  }
  return (
    <div style={styles.summary}>
      <div style={styles.description}>
        <div>{renderDeckName()}</div>
        <div>Master: {deck.master}</div>
      </div>
      <DeckBreakdown cards={deck.cards} />
    </div>
  );
}
