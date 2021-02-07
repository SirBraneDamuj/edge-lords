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
  return (
    <div className={'card-combat-stat'}>
      {`${stat} ${label && mapping[label]}`}
    </div>
  );
}