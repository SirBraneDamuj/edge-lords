import { Card } from '../card/types';

import { Element, CardType } from '../card/types';

const elementMapping = {
  [Element.FIRE]: 1,
  [Element.HEAVEN]: 2,
  [Element.EARTH]: 3,
  [Element.WATER]: 4,
};

const cardTypeMapping = {
  [CardType.MASTER]: 0,
  [CardType.NATIAL]: 1,
  [CardType.SPELL]: 2,
};

export function sortedCardList(
  cards: Array<Card>,
): Array<Card> {
  return cards.sort((a, b) => {
    const cardTypeSort = cardTypeMapping[a.cardType] - cardTypeMapping[b.cardType];
    if (cardTypeSort !== 0) {
      return cardTypeSort;
    } else {
      const elementSort = ((a.element && elementMapping[a.element]) || 99) -
          ((b.element && elementMapping[b.element]) || 99);
      if (elementSort !== 0) {
        return elementSort;
      } else {
        if (a.name === b.name) {
          return 0;
        } else if (a.name < b.name) {
          return -1;
        } else {
          return 1;
        }
      }
    }
  });
}
