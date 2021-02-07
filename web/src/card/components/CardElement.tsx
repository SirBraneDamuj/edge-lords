import React from 'react';
import { Element } from '../types';

interface Props {
  element?: Element
}

const mapping = {
  [Element.FIRE]: '🔥',
  [Element.HEAVEN]: '🕊️',
  [Element.EARTH]: '🗿',
  [Element.WATER]: '🌊',
};

export default function CardElement({
  element
}: Props): JSX.Element {
  return (
    <div className={'card-element'}>{element && mapping[element] || '' }</div>
  );
}