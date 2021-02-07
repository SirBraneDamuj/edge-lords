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
  return (
    <div className={'card-icons'}>
      <CardElement element={element} />
      <div className={'card-icons-2'}>
        <RangeIcon range={range} />
        <SpeedIcon speed={speed} />
      </div>
    </div>
  );
}
