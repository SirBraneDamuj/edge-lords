import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { endTurn } from '../../action';
import { GameContext, GameContextProvider, GameMode } from '../../context';
import { GamePerspective } from '../../types';
import CreaturesGrid from './CreaturesGrid';
import PlayerHand from './PlayerHand';
import SelectedEntityDetail from './SelectedEntityDetail';

function GameDetail(): JSX.Element | null {
  const context = useContext(GameContext);
  if (!context) return null;

  const { state, dispatch } = context;
  const { game, mode } = state;
  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'row' as const,
      justifyContent: 'space-between',
    },
  };

  function renderSelectedDetail() {
    return (
      <div>
        <h3>Details</h3>
        <SelectedEntityDetail />
      </div>
    );
  }

  function renderMovePrompt() {
    return (
      <div>
        <h3>Moving</h3>
        <p>Select a space to move to.</p>
      </div>
    );
  }

  function renderAttackPrompt() {
    return (
      <div>
        <h3>Attacking</h3>
        <p>Select an opponent&apos;s creature to target.</p>
      </div>
    );
  }
  
  // TODO: keep showing the card detail here
  function renderSummonPrompt() {
    return (
      <div>
        <h3>Summoning</h3>
        <p>Select where you&apos;d like to summon this Natial.</p>
      </div>
    );
  }

  // TODO: keep showing the card detail here
  function renderCastPrompt() {
    return (
      <div>
        <h3>Casting</h3>
        <p>Select the target(s) for this spell.</p>
      </div>
    );
  }

  function renderRightSide() {
    switch (mode) {
    case GameMode.VIEW: {
      return renderSelectedDetail();
    }
    case GameMode.MOVING: {
      return renderMovePrompt();
    }
    case GameMode.ATTACKING: {
      return renderAttackPrompt();
    }
    case GameMode.SUMMONING: {
      return renderSummonPrompt();
    }
    case GameMode.PLAYING_SPELL: {
      return renderCastPrompt();
    }
    }
  }

  return (
    <div>
      <div style={styles.container}>
        <div>
          <h2>Game</h2>
          <h3 style={{ textAlign: 'center' }}>
            {game.opponent.activePlayer ? '⭐ ' : ''}{game.opponent.name} | {game.opponent.mana}/{game.opponent.maxMana} 💎 | {game.opponent.handCount} ✋ / {game.opponent.deckCount} 🎴
          </h3>
          <CreaturesGrid side={'opponent'} />
          <hr />
          <CreaturesGrid side={'self'} />
          <div style={{ textAlign: 'center' }}>
            <h3 style={{ textAlign: 'center' }}>
              {game.self.activePlayer ? '⭐ ' : ''}{game.self.name} | {game.self.mana}/{game.self.maxMana} 💎 | {game.self.deckCount} 🎴
            </h3>
            {game.self.activePlayer && <button onClick={() => endTurn(state, dispatch)}>End Turn</button>}
          </div>
        </div>
        {renderRightSide()}
      </div>
      <PlayerHand />
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