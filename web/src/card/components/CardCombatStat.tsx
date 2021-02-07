import React from 'react';

interface Props {
  stat: number;
  label: 'ATK' | 'HP';
}

export default function CardCombatStat({
  stat,
  label,
}: Props): JSX.Element {
  return (
    <div className={'card-combat-stat'}>
      <div>{label}</div>
      <div>{stat}</div>
    </div>
  );
}