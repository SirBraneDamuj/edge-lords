import React, { useContext } from 'react';
import CardDetail from '../../../card/components/CardDetail';
import CardList from '../../../card/components/CardList';
import { CardsContext } from '../../../card/context';
import { Card } from '../../../card/types';

interface Props {
  master: string
  onSelect: (c: Card) => void
}

export default function MasterSelect({
  master,
  onSelect,
}: Props): JSX.Element | null {
  const { masters, cardsReady } = useContext(CardsContext);
  if (!cardsReady) return null;
  const cards =  Object.values(masters);
  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'row' as const,
    },
    select: {
      width: '50%',
      height: 300,
      overflowY: 'scroll' as const,
    },
    detail: {
      width: '50%',
      paddingLeft: 5,
    }
  };
  return (
    <div>
      <div>Master: {master ?? 'Unselected'}</div>
      <div style={styles.container}>
        <div style={styles.select}>
          <CardList onCardSelect={onSelect} wrap={true} cards={cards} selectable={true} />
        </div>
        <div style={styles.detail}>
          {!!master && <CardDetail card={masters[master]} />}
        </div>
      </div>
    </div>
  );
}
