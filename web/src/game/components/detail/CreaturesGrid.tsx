import React, { useContext } from 'react';
import { selectGridCell } from '../../action';
import { CreatureSide, GameContext } from '../../context';
import { CreaturePosition } from '../../types';
import CreatureGridCell from './CreatureGridCell';

interface EmptyProps {
  magicCrystal: boolean
  onClick: () => void
}

function EmptyGridSpace({
  magicCrystal,
  onClick,
}: EmptyProps) {
  const styles = {
    display: 'flex',
    flexDirection: 'column' as const,
    justifyContent: 'space-around',
    alignItems: 'center',
    width: 125,
    height: 105,
    border: '1px black solid',
    margin: 2,
  };
  return (
    <div onClick={onClick} style={styles}>
      {magicCrystal ? '💎' : ''}
    </div>
  );
}

interface Props {
  side: CreatureSide
}

export default function CreaturesGrid({
  side
}: Props): JSX.Element | null {
  const context = useContext(GameContext);
  if (!context) return null;
  const { state, dispatch } = context;
  const { game, selectedCreature } = state;

  const onCellClick = (position: CreaturePosition) => () => selectGridCell(
    side,
    position,
    state,
    dispatch
  );

  const flip = side === 'opponent';
  const magicCrystals = game[side].magicCrystals;
  const styles = {
    container: {
      display: 'flex',
      flexDirection: !flip ? 'column' as const : 'column-reverse' as const,
      justifyContent: 'space-between',
    },
    row: {
      display: 'flex',
      flexDirection: !flip ? 'row' as const : 'row-reverse' as const,
      justifyContent: 'space-around',
    }
  };
  const positions = game[side].creatures.reduce<Record<CreaturePosition, (JSX.Element | null)>>((p, creature) => {
    const selected = !!(selectedCreature?.side === side && selectedCreature?.position === creature.position);
    p[creature.position] = (
      <CreatureGridCell
        key={creature.position}
        onClick={onCellClick(creature.position)}
        creature={creature}
        selected={selected}
      />
    );
    return p;
  }, {
    [CreaturePosition.BACK_ONE]: null,
    [CreaturePosition.BACK_TWO]: null,
    [CreaturePosition.BACK_THREE]: null,
    [CreaturePosition.FRONT_ONE]: null,
    [CreaturePosition.FRONT_TWO]: null,
    [CreaturePosition.FRONT_THREE]: null,
    [CreaturePosition.FRONT_FOUR]: null,
  });
  const frontCells = [
    CreaturePosition.FRONT_ONE,
    CreaturePosition.FRONT_TWO,
    CreaturePosition.FRONT_THREE,
    CreaturePosition.FRONT_FOUR,
  ].map((p) => {
    return positions[p] ??
      <EmptyGridSpace key={p} onClick={onCellClick(p)} magicCrystal={magicCrystals.includes(p)} />;
  });
  const frontRow = <div style={styles.row}>{frontCells}</div>;
  const backCells = [
    CreaturePosition.BACK_ONE,
    CreaturePosition.BACK_TWO,
    CreaturePosition.BACK_THREE,
  ].map((p) => {
    return positions[p] ??
      <EmptyGridSpace key={p} onClick={onCellClick(p)} magicCrystal={magicCrystals.includes(p)} />;
  });
  const backRow = <div style={styles.row}>{backCells}</div>;
  return (
    <div style={styles.container}>
      {frontRow}
      {backRow}
    </div>
  );
}
