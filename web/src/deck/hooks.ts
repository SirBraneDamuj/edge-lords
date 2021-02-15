import { useEffect, useState } from 'react';
import { Deck } from './types';

export function useDecks(): Deck[] {
  const [decks, setDecks] = useState<Deck[]>([]);
  useEffect(() => {
    const request = new Request('/api/users/me');
    fetch(request)
      .then(response => response.json())
      .then(({ decks }) => setDecks(decks));
  }, [setDecks]);
  return decks;
}
