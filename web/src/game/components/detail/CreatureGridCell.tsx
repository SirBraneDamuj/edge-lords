import React, { CSSProperties } from 'react';
import CardCombatStat from '../../../card/components/detail/CardCombatStat';
import CardIcons from '../../../card/components/detail/CardIcons';
import CardManaCost from '../../../card/components/detail/CardManaCost';
import { ActivationState, Creature } from '../../types';
import CreatureGuard from './CreatureGuard';
import CreatureSealCount from './CreatureSealCount';

interface Props {
  creature: Creature
  selected: boolean
  onClick: (c: Creature) => void
}

function activationStateStyles(activationState: ActivationState, sealCount: number): CSSProperties {
  if (sealCount > 0) {
    return {
      backgroundColor: '#aaa',
    };
  }
  switch (activationState) {
  case ActivationState.ACTIVATED:
  case ActivationState.NOT_READY:
    return {
      backgroundColor: '#aaa',
    };
  default:
    return {};
  }
}

export default function CreatureGridCell({
  creature,
  selected = false,
  onClick,
}: Props): JSX.Element {
  const {
    card: {
      cardName,
      manaCost,
    },
    activationState,
    attack,
    hp,
    range,
    speed,
    element,
    sealCount,
    canUseSkill,
    guardCount,
    neighborGuardCount,
  } = creature;
  let creatureSkillIndicator = '';
  if (canUseSkill) {
    creatureSkillIndicator = ' ✅';
  } else if (canUseSkill === false) {
    creatureSkillIndicator = ' ❎';
  }
  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'column' as const,
      justifyContent: 'space-around',
      width: 125,
      height: 100,
      border: selected ? '3px solid black' : '1px solid black',
      margin: 2,
      padding: 5,
      cursor: 'pointer',
      ...activationStateStyles(activationState, sealCount)
    },
    header: {
      display: 'flex',
      flexDirection: 'row' as const,
      justifyContent: 'space-between',
    },
    mid: {
      textAlign: 'center' as const,
    },
    footer: {
      display: 'flex',
      flexDirection: 'row' as const,
      justifyContent: 'space-between',
    }
  };
  return (
    <div style={styles.container} onClick={() => onClick(creature)}>
      <div style={styles.header}>
        <CardIcons range={range} speed={speed} element={element??undefined} />
        <CardManaCost manaCost={manaCost} />
      </div>
      <div style={styles.mid}>{cardName}{creatureSkillIndicator}</div>
      <div style={styles.footer}>
        <CardCombatStat stat={attack} label={'ATK'} />
        <CreatureSealCount count={sealCount} /> 
        <CreatureGuard guardCount={guardCount} neighborGuard={neighborGuardCount > 0} />
        <CardCombatStat stat={hp} label={'HP'} />
      </div>
    </div>
  );
}
