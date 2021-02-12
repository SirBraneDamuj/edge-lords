import React from 'react';
import { Creature, CreaturePosition } from '../../types';
import CreatureDetail from './CreatureDetail';

function EmptyGridSpace() {
  const styles = {
    width: 200,
    height: 105,
    border: '1px black solid',
    margin: 2,
  };
  return (
    <div style={styles}></div>
  );
}

interface Props {
  creatures: Creature[]
  flip: boolean
}

export default function CreaturesGrid({
  creatures,
  flip,
}: Props): JSX.Element {
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
  const positions = creatures.reduce<Record<CreaturePosition, (JSX.Element | null)>>((p, creature) => {
    console.log(creature.position);
    p[creature.position] = <CreatureDetail creature={creature} />;
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
  console.log(positions);
  const frontRow = (
    <div style={styles.row}>
      {positions[CreaturePosition.FRONT_ONE] ?? <EmptyGridSpace />}
      {positions[CreaturePosition.FRONT_TWO] ?? <EmptyGridSpace />}
      {positions[CreaturePosition.FRONT_THREE] ?? <EmptyGridSpace />}
      {positions[CreaturePosition.FRONT_FOUR] ?? <EmptyGridSpace />}
    </div>
  );
  const backRow = (
    <div style={styles.row}>
      {positions[CreaturePosition.BACK_ONE] ?? <EmptyGridSpace />}
      {positions[CreaturePosition.BACK_TWO] ?? <EmptyGridSpace />}
      {positions[CreaturePosition.BACK_THREE] ?? <EmptyGridSpace />}
    </div>
  );
  return (
    <div style={styles.container}>
      {frontRow}
      {backRow}
    </div>
  );
}
