import React, { useContext, useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import CardList from '../../../card/components/CardList';
import { CardsContext } from '../../../card/context';
import { Card } from '../../../card/types';
import { useAuth } from '../../../user/hooks';
import { sortedCardList } from '../../model';
import { Deck } from '../../types';
import DeckBreakdown from '../list/DeckBreakdown';

interface DeckDetailParams {
  deckId: string
}

interface Props {
  cards: Record<string, number>
}

export function DeckDetail({
  cards
}: Props): JSX.Element {
  const { natials, spells, cardsReady } = useContext(CardsContext);

  if (!cardsReady) {
    return (
      <div>Loading...</div>
    );
  }

  const contents = sortedCardList(
    Object.entries(cards)
      .reduce((list, [cardName, cardCount]) => {
        for (let i=0; i<cardCount; i++) {
          const card = natials[cardName] ?? spells[cardName];
          if (!card) {
            throw new Error();
          }
          list.push(card);
        }
        return list;
      }, new Array<Card>())
  );
  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'row' as const,
      alignItems: 'flex-start',
    },
    breakdown: {
      marginRight: '25%',
    },
    cards: {
      borderRight: '1px black solid',
    }
  };
  return (
    <div>
      <div style={styles.container}>
        <div style={styles.cards}>
          <CardList cards={contents} />
        </div>
        <div style={styles.breakdown}>
          <DeckBreakdown cards={cards} />
        </div>
      </div>
    </div>
  );
}

export default function DeckDetailForDeckId(): JSX.Element {
  const { deckId } = useParams<DeckDetailParams>();
  const { cardsReady } = useContext(CardsContext);
  const [deck, setDeck] = useState<Deck | null>(null);
  useAuth();

  useEffect(() => {
    if (!cardsReady) return;
    const request = new Request(`/api/decks/${deckId}`);
    fetch(request)
      .then((response) => response.json())
      .then((deck) => setDeck(deck));
  }, [cardsReady, setDeck]);

  if (!deck) { return <div>Loading...</div>; }

  return (
    <>
      <h2>Deck: {deck?.name} - <Link to={`/decks/edit/${deckId}`}>Edit</Link></h2>
      <DeckDetail cards={deck.cards} />
    </>
  );
}
