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
  const onSkillClick = () => dispatch({ type: 'begin_skill' });

  function availableActions(): (JSX.Element | null)[] {
    const activationState = creature.activationState;
    const canUseSkill = !!card.targetingMode;
    switch (activationState) {
    case ActivationState.MOVED: {
      return [
        <button onClick={onAttackClick} key={0}>Attack</button>,
        canUseSkill ? <button onClick={onSkillClick} key={1}>Skill</button> : null,
      ];
    }
    case ActivationState.READY_AGAIN:
    case ActivationState.READY: {
      return [
        <button onClick={onMoveClick} key={0}>Move</button>,
        <button onClick={onAttackClick} key={1}>Attack</button>,
        canUseSkill ? <button onClick={onSkillClick} key={2}>Skill</button> : null,
      ];
    }
    default:
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
