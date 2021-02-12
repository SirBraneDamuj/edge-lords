import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { GamePerspective } from '../../types';
import CreaturesGrid from './CreaturesGrid';

interface Props {
  game: GamePerspective
}

function GameDetail({
  game
}: Props): JSX.Element {
  return (
    <div>
      <h2>Game</h2>
      <h3 style={{ textAlign: 'center' }}>{game.opponent.name}</h3>
      <CreaturesGrid creatures={game.opponent.creatures} flip={true} />
      <hr />
      <CreaturesGrid creatures={game.self.creatures} flip={false} />
      <h3 style={{ textAlign: 'center' }}>{game.self.name}</h3>
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
    fetch(new Request(`/games/${gameId}`))
      .then((response) => response.json())
      .then((body) => setGame(body));
  }, [gameId, setGame]);

  if (game === null) {
    return <div>Loading...</div>;
  }

  return (
    <GameDetail game={game} />
  );
}
