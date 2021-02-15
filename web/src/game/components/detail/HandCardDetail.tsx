import React, { useContext } from 'react';
import CardDetail from '../../../card/components/CardDetail';
import { Card, CardType } from '../../../card/types';
import { GameContext } from '../../context';

interface Props {
  card: Card
  showActions: boolean
}

export default function HandCardDetail({
  card,
  showActions,
}: Props): JSX.Element | null {
  const context = useContext(GameContext);
  if (!context) return null;

  const { dispatch } = context;

  const onSummonClick = () => dispatch({
    type: 'begin_summon',
  });
  const onCastClick = () => dispatch({
    type: 'begin_cast',
  });

  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'column' as const,
    },
    buttons: {
      display: 'flex',
      flexDirection: 'column' as const,
    }
  };

  function cardActions(): JSX.Element[] {
    if (!showActions) return [];
    switch (card.cardType) {
    case (CardType.NATIAL): {
      return [
        <button key={0} onClick={onSummonClick}>Summon</button>
      ];
    }
    case (CardType.SPELL): {
      return [
        <button key={0} onClick={onCastClick}>Cast</button>
      ];
    }
    default: {
      return [];
    }
    }
  }

  return (
    <div style={styles.container}>
      <CardDetail card={card} />
      <div style={styles.buttons}>
        {cardActions()}
      </div>
    </div>
  );
}
