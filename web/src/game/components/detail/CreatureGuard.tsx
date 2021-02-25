import React from 'react';

interface Props {
  guardCount: number
  neighborGuard: boolean
}

export default function CreatureGuard({
  guardCount,
  neighborGuard,
}: Props): JSX.Element | null {
  const styles = {
    display: 'flex',
    flexDirection: 'column' as const,
  };
  if (guardCount == 0 && !neighborGuard) return null;
  const displayCount = neighborGuard ? '' : guardCount;
  return (
    <div style={styles}>
      {displayCount}🛡️
    </div>
  );
}
