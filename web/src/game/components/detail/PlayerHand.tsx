import React, { useContext } from 'react';
import CardList from '../../../card/components/CardList';
import { CardsContext } from '../../../card/context';
import { Card } from '../../../card/types';
import { selectHandCard } from '../../action';
import { GameContext } from '../../context';

export default function PlayerHand(): JSX.Element | null {
  const { cardsReady, natials, spells } = useContext(CardsContext);
  const context = useContext(GameContext);
  if (!context || !cardsReady) return null;

  const { state, dispatch } = context;
  const {
    game: { self: { hand } },
    selectedCard,
  } = state;

  const onCardSelect = (c: Card, i: number) => selectHandCard(
    i,
    hand[i],
    c,
    state,
    dispatch,
  );

  const handItems = hand.map((handCard) => {
    const cardDetails = natials[handCard.cardName] ?? spells[handCard.cardName];
    if (!cardDetails) {
      return null;
    }
    return {
      ...cardDetails,
      manaCost: handCard.manaCost,
    };
  }).filter((c): c is Card => !!c);

  const selectedHandPosition = selectedCard ? selectedCard.handPosition : -1;

  return (
    <CardList
      highlightPosition={selectedHandPosition}
      cards={handItems}
      wrap={true}
      selectable={true}
      onCardSelect={onCardSelect}
    />
  );
}
