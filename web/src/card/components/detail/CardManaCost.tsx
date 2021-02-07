import React from 'react';

interface Props {
  manaCost: number;
}

export default function CardManaCost({
  manaCost
}: Props): JSX.Element {
  return (
    <div className={'card-mana'}>{manaCost && `${manaCost} 💎`}</div>
  );
}