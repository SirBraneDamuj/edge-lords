import React, { useContext } from 'react';
import { CardsContext } from '../context';
import { useAuth } from '../../user/hooks';
import { Card } from '../types';
import CardList from './CardList';

export default function AllCardsList(): JSX.Element {
  useAuth();

  const { masters, natials, spells } = useContext(CardsContext);

  const masterCards: Array<Card> = [];
  for (const master of Object.keys(masters)) {
    masterCards.push(masters[master]);
  }
  const natialCards: Array<Card> = [];
  for (const natial of Object.keys(natials)) {
    natialCards.push(natials[natial]);
  }
  const spellCards: Array<Card> = [];
  for (const spell of Object.keys(spells)) {
    spellCards.push(spells[spell]);
  }

  return (
    <>
      <h2>Masters</h2>
      <CardList cards={masterCards} />
      <h2>Natials</h2>
      <CardList cards={natialCards} />
      <h2>Spells</h2>
      <CardList cards={spellCards} />
    </>
  );
}
