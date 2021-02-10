import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { useAuth } from '../../../user/hooks';
import { Deck } from '../../types';
import DeckSummary from './DeckSummary';

export default function DeckList(): JSX.Element {
  const [decks, setDecks] = useState<Array<Deck>>([]);
  const history = useHistory();
  useAuth();
  useEffect(() => {
    const request = new Request('/users/me');
    fetch(request)
      .then(response => response.json())
      .then(({ decks }) => setDecks(decks));
  }, [setDecks]);
  const styles = {
    newDeck: {
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
  const deckSummaries = decks.map((deck) => (
    <div key={deck.id} style={styles.item}>
      <DeckSummary  deck={deck} />
    </div>
  ));
  return (
    <>
      <h2>Decks</h2>
      <div style={styles.newDeck} onClick={() => history.push('/decks/new')}>
        New Deck
      </div>
      <div className={'deck-list'}>
        {deckSummaries}
      </div>
    </>
  );
}
