import { findByLabelText } from '@testing-library/react';
import React from 'react';

interface Props {
  stat: number;
  label: 'ATK' | 'HP';
}

const mapping = {
  'ATK': '⚔️',
  'HP': '🛡️',
};

export default function CardCombatStat({
  stat,
  label,
}: Props): JSX.Element {
  const styles = {
    display: 'flex',
    flexDirection: 'column' as const,
  };
  return (
    <div style={styles}>
      {`${stat} ${label && mapping[label]}`}
    </div>
  );
}
