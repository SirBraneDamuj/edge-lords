import React, { useContext } from 'react';
import { CardsContext } from '../../../card/context';
import { GameContext } from '../../context';
import CreatureDetail from './CreatureDetail';
import HandCardDetail from './HandCardDetail';

export default function SelectedEntityDetail(): JSX.Element | null {
  const {
    natials,
    spells,
    masters,
    cardsReady
  } = useContext(CardsContext);
  const gameContext = useContext(GameContext);
  if (!cardsReady || !gameContext) return <div>Loading...</div>;
  const {
    state: {
      game, selectedCreature, selectedCard
    },
  } = gameContext;
  const {
    self: {
      activePlayer
    }
  } = game;

  if (selectedCreature !== null) {
    const { side, position } = selectedCreature;
    const creature = game[side].creatures.find((c) => c.position === position);
    if (!creature) return null;
    const card = natials[creature.card.cardName] ?? spells[creature.card.cardName] ?? masters[creature.card.cardName];
    if (!card) return null;
    return (
      <div>
        <h3>Details</h3>
        <CreatureDetail creature={creature} card={card} showActions={side === 'self' && activePlayer} />
      </div>
    );
  } else if (selectedCard !== null) {
    const { handPosition } = selectedCard;
    const cardName = game.self.hand[handPosition]?.cardName;
    if (!cardName) return null;
    const card = natials[cardName] ?? spells[cardName] ?? masters[cardName];
    if (!card) return null;

    return (
      <div>
        <h3>Details</h3>
        <HandCardDetail showActions={activePlayer} card={card} />
      </div>
    );
  } else {
    return null;
  }
}
