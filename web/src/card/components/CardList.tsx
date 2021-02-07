import React, { ReactElement, useContext } from 'react';
import CardDetail from './CardDetail';
import { CardsContext } from '../context';
import './CardList.css';
import { useAuth } from '../../user/hooks';

export default function CardList(): JSX.Element {
  useAuth();

  const { masters, natials, spells } = useContext(CardsContext);

  const masterCards: Array<ReactElement> = [];
  for (const master of Object.keys(masters)) {
    masterCards.push(<CardDetail key={master} card={masters[master]} />);
  }
  const natialCards: Array<ReactElement> = [];
  for (const natial of Object.keys(natials)) {
    natialCards.push(<CardDetail key={natial} card={natials[natial]} />);
  }

  return (
    <>
      <h2>Masters</h2>
      <div className={'card-grid'}>
        {masterCards}
      </div>
      <h2>Natials</h2>
      <div className={'card-grid'}>
        {natialCards}
      </div>
    </>
  );
}
