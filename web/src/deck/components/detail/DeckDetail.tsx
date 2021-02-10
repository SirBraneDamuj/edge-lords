import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import CardList from '../../../card/components/CardList';
import { CardsContext } from '../../../card/context';
import { CardType, Element } from '../../../card/types';
import { Card } from '../../../card/types';
import { useAuth } from '../../../user/hooks';
import { Deck } from '../../types';
import DeckBreakdown from '../list/DeckBreakdown';

interface DeckDetailParams {
  deckId: string
}

const elementMapping = {
  [Element.FIRE]: 1,
  [Element.HEAVEN]: 2,
  [Element.EARTH]: 3,
  [Element.WATER]: 4,
};

const cardTypeMapping = {
  [CardType.MASTER]: 0,
  [CardType.NATIAL]: 1,
  [CardType.SPELL]: 2,
};

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

  const contents = Object.entries(cards)
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
    .sort((a, b) => {
      const cardTypeSort = cardTypeMapping[a.cardType] - cardTypeMapping[b.cardType];
      if (cardTypeSort !== 0) {
        return cardTypeSort;
      } else {
        return ((a.element && elementMapping[a.element]) || 99) - ((b.element && elementMapping[b.element]) || 99);
      }
    });
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

  useEffect(() => {
    if (!cardsReady) return;
    const request = new Request(`/decks/${deckId}`);
    fetch(request)
      .then((response) => response.json())
      .then((deck) => setDeck(deck));
  }, [setDeck]);

  if (!deck) { return <div>Loading...</div>; }
  useAuth();

  return (
    <>
      <h2>Deck: {deck?.name}</h2>
      <DeckDetail cards={deck.cards} />
    </>
  );
}
