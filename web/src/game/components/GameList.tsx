import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { GameListEntry } from '../types';
import GameSummary from './GameSummary';

export default function GameList(): JSX.Element {
  const [games, setGames] = useState<GameListEntry[] | null>(null);
  const history = useHistory();

  useEffect(() => {
    fetch(new Request('/games'))
      .then((response) => response.json())
      .then(({ games }) => setGames(games));
  }, [setGames]);
  const styles = {
    newGame: {
      textAlign: 'center' as const,
      padding: 15,
      margin: '1rem',
      border: '1px black solid',
      cursor: 'pointer',
    },
    item: {
      margin: '1rem',
    }
  };
  if (games == null) return <div>Loading...</div>;
  return (
    <div>
      <h2>Games</h2>
      <div style={styles.newGame} onClick={() => history.push('/decks/new')}>
        New Game
      </div>
      <div className={'deck-list'}>
        {
          games.map((game, index) => <GameSummary key={index} game={game} />)
        }
      </div>
    </div>
  );
}
