import React, { useContext } from 'react';
import { CardsContext } from '../../../card/context';
import { Deck } from '../../types';

interface Props {
  deck: Deck
}

export default function NatialsBreakdown({
  deck
}: Props): JSX.Element {
  const { spells } = useContext(CardsContext);
  const spellCards = Object.keys(deck.cards)
    .filter((card) => !!spells[card])
    .map((card) => spells[card]);
  const numberSpells = spellCards.reduce((sum, card) => sum + deck.cards[card.name], 0);
  const totalCost = spellCards.reduce((sum, card) => sum + card.manaCost, 0);
  const averageCost = (totalCost / numberSpells).toFixed(1);
  return (
    <div>
      <div>Spells</div>
      <div>Total: {numberSpells}</div>
      <div>Average Cost: {averageCost}</div>
    </div>
  );
}
