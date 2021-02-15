import React from 'react';
import { useHistory } from 'react-router-dom';
import { useAuth } from '../../../user/hooks';
import { useDecks } from '../../hooks';
import { Deck } from '../../types';
import DeckSummary from './DeckSummary';

export enum DeckListMode {
  DRILL_DOWN = 'DRILL_DOWN',
  SELECTION = 'SELECTION',
}

interface Props {
  decks: Deck[]
  mode: DeckListMode
  onSelect?: (deck: Deck) => void
}

export function DeckList({
  decks,
  mode,
  onSelect = () => {return;},
}: Props): JSX.Element {
  const history = useHistory();
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
      cursor: mode === DeckListMode.SELECTION ? 'pointer' : 'arrow',
    }
  };
  const onDeckClick = (deck: Deck) => () => onSelect(deck);
  const deckSummaries = decks.map((deck) => (
    <div key={deck.id} style={styles.item} onClick={onDeckClick(deck)}>
      <DeckSummary mode={mode} deck={deck} />
    </div>
  ));
  return (
    <>
      {
        mode === DeckListMode.DRILL_DOWN && (
          <div style={styles.newDeck} onClick={() => history.push('/decks/new')}>
            New Deck
          </div>
        )
      }
      <div className={'deck-list'}>
        {deckSummaries}
      </div>
    </>
  );
}

export function DeckListPage(): JSX.Element {
  const decks = useDecks();
  useAuth();

  return (
    <div>
      <h2>Decks</h2>
      <DeckList mode={DeckListMode.DRILL_DOWN} decks={decks} />
    </div>
  );
}
