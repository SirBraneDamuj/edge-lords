import React from 'react';
import { Element } from '../types';

interface Props {
  element?: Element
}

export default function CardElement({
  element
}: Props): JSX.Element {
  return (
    <div className={'card-element'}>{element ?? ''}</div>
  );
}