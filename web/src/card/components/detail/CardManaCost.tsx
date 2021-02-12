import React from 'react';

interface Props {
  manaCost: number;
}

export default function CardManaCost({
  manaCost
}: Props): JSX.Element {
  const emoji = manaCost > 0 ? `${manaCost} 💎` : '👑';
  return (
    <div className={'card-mana'}>{emoji}</div>
  );
}
