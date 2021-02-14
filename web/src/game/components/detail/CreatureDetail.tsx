import React, { useContext } from 'react';
import CardDetail from '../../../card/components/CardDetail';
import { Card } from '../../../card/types';
import { GameContext } from '../../context';
import { ActivationState, Creature } from '../../types';

interface Props {
  creature: Creature
  card: Card
  showActions: boolean
}

export default function CreatureDetail({
  creature,
  card,
  showActions,
}: Props): JSX.Element | null {
  const context = useContext(GameContext);
  if (!context) return null;
  const { dispatch } = context;
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

  const onMoveClick = () => dispatch({ type: 'begin_move' });
  const onAttackClick = () => dispatch({ type: 'begin_attack' });

  function availableActions(): JSX.Element[] {
    const activationState = creature.activationState;
    if (activationState === ActivationState.MOVED) {
      return [
        <button onClick={onAttackClick} key={0}>Attack</button>,
      ];
    } else if (activationState === ActivationState.READY || ActivationState.READY_AGAIN) {
      return [
        <button onClick={onMoveClick} key={0}>Move</button>,
        <button onClick={onAttackClick} key={1}>Attack</button>,
      ];
    } else {
      return [];
    }
  }
  return (
    <div style={styles.container}>
      <CardDetail card={realCard} />
      {
        showActions &&
        <div style={styles.buttons}>
          {availableActions()}
        </div>
      }
    </div>
  );
}
