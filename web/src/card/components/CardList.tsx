import React, { ReactElement } from 'react';
import { Card } from '../types';
import CardDetail from './CardDetail';

interface Props {
  cards: Array<Card>
}

export default function CardList({
  cards
}: Props): JSX.Element {
  const cardDetails: Array<ReactElement> = [];
  for (const index in cards) {
    cardDetails[index] = <CardDetail card={cards[index]} key={index} />;
  }
  return (
    <div className={'card-grid'}>
      {cardDetails}
    </div>
  );
}
