import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import CardList from '../../../card/components/CardList';
import { CardsContext } from '../../../card/context';
import { CardType, Element } from '../../../card/types';
import { Card } from '../../../card/types';
import { Deck } from '../../types';
import DeckBreakdown from '../list/DeckBreakdown';
import DeckSummary from '../list/DeckSummary';
import './DeckDetail.css';

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

export default function DeckDetail(): JSX.Element {
  const { deckId } = useParams<DeckDetailParams>();
  const { natials, spells, cardsReady } = useContext(CardsContext);
  const [deck, setDeck] = useState<Deck | null>(null);

  useEffect(() => {
    if (!cardsReady) return;
    const request = new Request(`/decks/${deckId}`);
    fetch(request)
      .then((response) => response.json())
      .then((deck) => setDeck(deck));
  }, [natials, spells, setDeck]);

  if (!deck) {
    return (
      <div>Loading...</div>
    );
  }

  const cards = Object.entries(deck.cards)
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
  return (
    <div>
      <h2>Deck: {deck.name}</h2>
      <div className={'deck-detail-container'}>
        <CardList cards={cards} />
        <DeckBreakdown deck={deck} />
      </div>
    </div>
  );
}
