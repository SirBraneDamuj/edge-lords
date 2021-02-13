import React, { useContext, useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import CardList from '../../../card/components/CardList';
import { CardsContext } from '../../../card/context';
import { Card } from '../../../card/types';
import { useAuth } from '../../../user/hooks';
import { sortedCardList } from '../../model';
import { Deck } from '../../types';
import DeckBreakdown from '../list/DeckBreakdown';
import MasterSelect from './MasterSelect';

interface Props {
  name: string
  setName: (s: string) => void,
  master: string
  setMaster: (s: string) => void
  cards: Record<string, number>
  setCards: (c: Record<string, number>) => void
  loading: boolean
  error: string
  onSubmit: () => void | Promise<void>
}

function DeckBuilder({
  name,
  setName,
  master,
  setMaster,
  cards,
  setCards,
  error,
  loading,
  onSubmit
}: Props): JSX.Element {

  const { natials, spells, cardsReady } = useContext(CardsContext);

  if (!cardsReady) { return <div>Loading...</div>; }

  function addCard(cardName: string): void {
    if (loading) { return; }
    if (cards[cardName] === 3) { return; }
    if (Object.values(cards).reduce((t, i) => t + i, 0) >= 20) { return; }
    setCards({
      ...cards,
      [cardName]: (cards[cardName] ?? 0) + 1,
    });
  }

  function removeCard(cardName: string): void {
    if (loading) { return; }
    if ((cards[cardName] ?? 0) <= 0) {
      return;
    }
    setCards({
      ...cards,
      [cardName]: cards[cardName] - 1,
    });
  }
  const deckContents = sortedCardList(
    Object.entries(cards)
      .reduce((list, [cardName, cardCount]) => {
        for (let i=0; i<cardCount; i++) {
          const card = natials[cardName] ?? spells[cardName];
          list.push(card as Card);
        }
        return list;
      }, new Array<Card>())
  );

  const eligibleCards = [
    ...Object.values(natials).filter((c) => !cards[c.name] || cards[c.name] < 3),
    ...Object.values(spells).filter((c) => !cards[c.name] || cards[c.name] < 3),
  ];

  const styles = {
    container: {},
    nameForm: {
      display: 'flex',
      flexDirection: 'column' as const,
      width: '10rem',
    },
    formItem: {},
    contentsContainer: {
      display: 'flex',
      flexDirection: 'row' as const,
    },
    contents: {
      height: 300,
      overflowY: 'scroll' as const,
      width: '50%',
    },
    breakdown: {
      height: 300,
      width: '50%',
    },
    selectable: {
      height: 300,
      overflowX: 'scroll' as const,
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.nameForm}>
        <label htmlFor={'name'} style={styles.formItem}>Deck Name:</label>
        <input name={'name'} type={'text'} value={name} onChange={(e) => setName(e.target.value)} style={styles.formItem} />
      </div>
      <hr />
      <MasterSelect master={master} onSelect={(m) => setMaster(m.name)} />
      <hr />
      <div>Deck Contents:</div>
      <div style={styles.contentsContainer}>
        <div style={styles.contents}>
          <CardList cards={deckContents} wrap={true} selectable={true} onCardSelect={(c) => removeCard(c.name)} />
        </div>
        <div style={styles.breakdown}>
          <DeckBreakdown cards={cards} />
        </div>
      </div>
      <div>Cards:</div>
      <div style={styles.selectable}>
        <CardList cards={eligibleCards} wrap={false} selectable={true} onCardSelect={(c) => addCard(c.name)} />
      </div>
      {error && <div>error</div>}
      <button disabled={loading} onClick={onSubmit}>Save Deck</button>
    </div>
  );
}

export function NewDeckBuilder(): JSX.Element {
  const [name, setName] = useState('');
  const [master, setMaster] = useState('');
  const [cards, setCards] = useState<Record<string, number>>({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const history = useHistory();
  useAuth();

  async function submitDeck(): Promise<void> {
    if (loading) { return; }
    setLoading(true);
    const request = new Request('/api/decks', {
      method: 'POST',
      body: JSON.stringify({
        name,
        master,
        cards,
      }),
    });
    const response = await fetch(request);
    if (!response.ok) {
      setLoading(false);
      setError('Failed to save deck');
    } else {
      history.push('/decks');
    }
  }


  return (
    <DeckBuilder
      name={name}
      setName={setName}
      master={master}
      setMaster={setMaster}
      cards={cards}
      setCards={setCards}
      loading={loading}
      error={error}
      onSubmit={submitDeck}
    />
  );
}

interface EditDeckParams {
  deckId: string
}

export function EditDeckBuilder(): JSX.Element {
  const [name, setName] = useState('');
  const [master, setMaster] = useState('');
  const [cards, setCards] = useState<Record<string, number>>({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const history = useHistory();
  const { deckId } = useParams<EditDeckParams>();

  useAuth();
  useEffect(() => {
    setLoading(true);
    fetch(new Request(`/api/decks/${deckId}`))
      .then((response) => response.json())
      .then((json: Deck) => {
        setName(json.name);
        setMaster(json.master);
        setCards(json.cards);
        setLoading(false);
      });
  }, [setLoading, setName, setMaster, setCards]);

  async function submitDeck(): Promise<void> {
    if (loading) { return; }
    setLoading(true);
    const request = new Request(`/api/decks/${deckId}`, {
      method: 'PUT',
      body: JSON.stringify({
        name,
        master,
        cards,
      }),
    });
    const response = await fetch(request);
    if (!response.ok) {
      setLoading(false);
      setError('Failed to save deck');
    } else {
      history.push('/decks');
    }
  }

  if (loading) return <div>Loading...</div>;

  return (
    <DeckBuilder
      name={name}
      setName={setName}
      master={master}
      setMaster={setMaster}
      cards={cards}
      setCards={setCards}
      loading={loading}
      error={error}
      onSubmit={submitDeck}
    />
  );
}
