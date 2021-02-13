import React from 'react';
import CardDetail from '../../../card/components/CardDetail';
import { Card } from '../../../card/types';
import { ActivationState, Creature } from '../../types';

interface Props {
  creature: Creature
  card: Card
}

function availableActions(activationState: ActivationState): JSX.Element[] {
  if (activationState === ActivationState.MOVED) {
    return [
      <button key={0}>Attack</button>,
    ];
  } else if (activationState === ActivationState.READY || ActivationState.READY_AGAIN) {
    return [
      <button key={0}>Move</button>,
      <button key={1}>Attack</button>,
    ];
  } else {
    return [];
  }
}

export default function CreatureDetail({
  creature,
  card,
}: Props): JSX.Element {
  const realCard = {
    ...card,
    manaCost: creature.card.manaCost,
  };
  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'column' as const,
    },
    buttons: {
      display: 'flex',
      flexDirection: 'column' as const,
    }
  };
  return (
    <div style={styles.container}>
      <CardDetail card={realCard} />
      <div style={styles.buttons}>
        {availableActions(creature.activationState)}
      </div>
    </div>
  );
}
