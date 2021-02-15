import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { DeckList, DeckListMode } from '../../../deck/components/list/DeckList';
import { useDecks } from '../../../deck/hooks';
import { Deck } from '../../../deck/types';

export default function NewGame(): JSX.Element {
  const [selectedDeck, setSelectedDeck] = useState<Deck | null>(null);
  const [opponentDeckId, setOpponentDeckId] = useState('');
  const [goingFirst, setGoingFirst] = useState(false);
  const decks = useDecks();
  const history = useHistory();

  function ready(selectedDeck: Deck | null): selectedDeck is Deck {
    return selectedDeck !== null && opponentDeckId !== '';
  }

  async function submitNewGame() {
    if (!ready(selectedDeck)) return;
    const deckIds = goingFirst ? [selectedDeck.id, opponentDeckId] : [opponentDeckId, selectedDeck.id];
    const response = await fetch(new Request('/api/games', {
      method: 'POST',
      body: JSON.stringify({
        deckIds
      })
    }));
    if (response.ok) {
      history.push('/games');
    } else {
      alert('failed to create game :(');
    }
  }

  const selectedDeckText = selectedDeck ? `${selectedDeck.name} (id: ${selectedDeck.id})` : 'None';

  return (
    <div>
      <h2>New Game</h2>
      <div><strong>Selected Deck: </strong>{selectedDeckText}</div>
      <DeckList mode={DeckListMode.SELECTION} decks={decks} onSelect={setSelectedDeck} />
      <div>
        <label htmlFor={'opponentDeckId'}><strong>Opponent Deck ID: </strong></label>
        <input name={'opponentDeckId'} value={opponentDeckId} onChange={(e) => setOpponentDeckId(e.target.value)} />
      </div>
      <div>
        <label htmlFor={'goingFirst'}><strong>Are you going first? </strong></label>
        <input name={'goingFirst'} type={'checkbox'} checked={goingFirst} onChange={(e) => setGoingFirst(e.target.checked)} />
      </div>
      <div>
        {ready && <button onClick={submitNewGame}>Submit</button>}
      </div>
    </div>
  );
}
