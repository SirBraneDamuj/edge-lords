import React from 'react';
import { AttackRange } from '../types';

interface Props {
  range: AttackRange;
}

const mapping = {
  [AttackRange.MELEE]: '✊',
  [AttackRange.RANGED]: '🏹',
};

export default function RangeIcon({
  range
}: Props): JSX.Element {
  return (
    <div className={'range-icon'}>
      {range && mapping[range] || ''}
    </div>
  );
}