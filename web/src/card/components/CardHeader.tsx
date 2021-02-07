import React from 'react';
import { Element } from '../types';
import CardElement from './CardElement';
import CardManaCost from './CardManaCost';

interface Props {
  element?: Element;
  name: string;
  manaCost: number;
}

export default function CardHeader({
  element,
  name,
  manaCost,
}: Props): JSX.Element {
  return (
    <div className={'card-header'}>
      <CardElement element={element} />
      <div className={'card-name'}>{name}</div>
      <CardManaCost manaCost={manaCost} />
    </div>
  );
}