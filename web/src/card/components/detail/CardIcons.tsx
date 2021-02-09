import React from 'react';
import { ActionSpeed, AttackRange, Element } from '../../types';
import CardElement from './CardElement';
import RangeIcon from './RangeIcon';
import SpeedIcon from './SpeedIcon';

interface Props {
  element?: Element;
  speed: ActionSpeed;
  range: AttackRange;
}

export default function CardIcons({
  element,
  speed,
  range
}: Props): JSX.Element {
  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'row' as const,
    },
    iconsContainer: {
      display: 'flex',
      flexDirection: 'row' as const,
    }
  };
  return (
    <div style={styles.container}>
      <CardElement element={element} />
      <div style={styles.iconsContainer}>
        <RangeIcon range={range} />
        <SpeedIcon speed={speed} />
      </div>
    </div>
  );
}
