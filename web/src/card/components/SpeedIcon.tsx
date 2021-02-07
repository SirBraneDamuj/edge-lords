import React from 'react';
import { ActionSpeed } from '../types';

interface Props {
  speed: ActionSpeed;
}

const mapping = {
  [ActionSpeed.NORMAL]: '🐢',
  [ActionSpeed.FAST]: '⚡',
};

export default function RangeIcon({
  speed
}: Props): JSX.Element {
  return (
    <div className={'speed-icon'}>
      {speed && mapping[speed] || ''}
    </div>
  );
}