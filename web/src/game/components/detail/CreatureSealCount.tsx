import React from 'react';

interface Props {
  count: number
}

export default function CreatureSealCount({
  count,
}: Props): JSX.Element | null {
  if (!count) {
    return null;
  }
  return (
    <div>{count}❌</div>
  );
}
