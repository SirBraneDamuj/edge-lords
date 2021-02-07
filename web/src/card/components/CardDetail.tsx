import React from 'react';
import { Card } from '../types';
import CardHeader from './CardHeader';
import './CardDetail.css';
import CardMid from './CardMid';
import CardFooter from './CardFooter';

interface Props {
  card: Card;
}

export default function CardDetail({ card }: Props): JSX.Element {
  return (
    <>
      <div className={'card-container'}>
        <CardHeader card={card} />
        <CardMid
          attack={card.attack}
          hp={card.hp}
          cardType={card.cardType}
        />
        <CardFooter
          skillText={card.skillText}
          abilityText={card.abilityText}
        />
      </div>
    </>
  );
}
