import React from 'react';
import { Link } from 'react-router-dom';
import { GameListEntry } from '../../types';

interface Props {
  game: GameListEntry
}

export default function GameSummary({
  game: {
    id,
    deckId,
    deckName,
    opponentName,
    state,
  }
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
        <div><Link to={`/games/${id}`}>Opponent: {opponentName}</Link></div>
        <div><Link to={`/decks/${deckId}`}>Deck: {deckName}</Link></div>
        <div>State: {state}</div>
      </div>
    </div>
  );
}
