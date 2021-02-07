import React, { useEffect, useState } from 'react';
import { Deck } from '../../types';
import DeckSummary from './DeckSummary';

export default function DeckList(): JSX.Element {
  const [decks, setDecks] = useState<Array<Deck>>([]);
  useEffect(() => {
    const request = new Request('/users/me');
    fetch(request)
      .then(response => response.json())
      .then(({ decks }) => setDecks(decks));
  }, [setDecks]);
  const deckSummaries = decks.map((deck) => <DeckSummary key={deck.id} deck={deck} />);
  return (
    <>
      <h2>Decks</h2>
      <div className={'deck-list'}>
        {deckSummaries}
      </div>
    </>
  );
}
