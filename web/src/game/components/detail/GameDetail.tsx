import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { GameContext, GameContextProvider } from '../../context';
import { GamePerspective } from '../../types';
import CreaturesGrid from './CreaturesGrid';
import SelectedEntityDetail from './SelectedEntityDetail';

function GameDetail(): JSX.Element | null {
  const context = useContext(GameContext);
  if (!context) return null;

  const { state: { game } } = context;
  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'row' as const,
      justifyContent: 'space-between',
    },
  };

  return (
    <div style={styles.container}>
      <div>
        <h2>Game</h2>
        <h3 style={{ textAlign: 'center' }}>{game.opponent.name}</h3>
        <CreaturesGrid side={'opponent'} />
        <hr />
        <CreaturesGrid side={'self'} />
        <h3 style={{ textAlign: 'center' }}>{game.self.name}</h3>
      </div>
      <div>
        <h3>Details</h3>
        <SelectedEntityDetail />
      </div>
    </div>
  );
}

interface GameDetailParams {
  gameId: string
}

export default function GameDetailForGameIdParam(): JSX.Element {
  const [game, setGame] = useState<GamePerspective | null>(null);
  const { gameId } = useParams<GameDetailParams>();

  useEffect(() => {
    fetch(new Request(`/api/games/${gameId}`))
      .then((response) => response.json())
      .then((body) => setGame(body));
  }, [gameId, setGame]);

  if (game === null) {
    return <div>Loading...</div>;
  }

  return (
    <GameContextProvider initialState={game}>
      <GameDetail />
    </GameContextProvider>
  );
}
