import React, { useContext } from 'react';
import CardElement from '../../../card/components/detail/CardElement';
import { CardsContext } from '../../../card/context';
import { Element } from '../../../card/types';
import { Deck } from '../../types';

interface Props {
  deck: Deck
}

export default function NatialsBreakdown({
  deck
}: Props): JSX.Element {
  const { natials } = useContext(CardsContext);
  const natialCards = Object.keys(deck.cards)
    .filter((card) => !!natials[card])
    .map((card) => natials[card]);
  const elementBreakdown = natialCards.reduce((acc, card) => {
    if (card.element) {
      acc[card.element] += 1;
    }
    return acc;
  }, {
    [Element.FIRE]: 0,
    [Element.HEAVEN]: 0,
    [Element.EARTH]: 0,
    [Element.WATER]: 0,
  });
  const styles = {
    container: {
      display: 'flex',
      flexDirection: 'column' as const,
      justifyContent: 'space-around',
    },
    list: {
      display: 'flex',
      flexDirection: 'row' as const,
      justifyContent: 'space-between',
    },
    item: {
      display: 'flex',
      flexDirection: 'row' as const,
    },
  };
  return (
    <div style={styles.container}>
      <div>Natials</div>
      <div style={styles.list}>
        <div style={styles.item}>
          <CardElement element={Element.FIRE} />
          <div>{elementBreakdown[Element.FIRE]}</div>
        </div>
        <div style={styles.item}>
          <CardElement element={Element.HEAVEN} />
          <div>{elementBreakdown[Element.HEAVEN]}</div>
        </div>
        <div style={styles.item}>
          <CardElement element={Element.EARTH} />
          <div>{elementBreakdown[Element.EARTH]}</div>
        </div>
        <div style={styles.item}>
          <CardElement element={Element.WATER} />
          <div>{elementBreakdown[Element.WATER]}</div>
        </div>
      </div>
    </div>
  );
}
