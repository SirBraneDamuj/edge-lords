import React, { useEffect, useState } from 'react';
import { Card } from './types';

interface CardsMap {
  masters: { [key:string]: Card }
  natials: { [key:string]: Card }
  spells: { [key:string]: Card }
  cardsReady: boolean
}

const defaultValue = (): CardsMap => ({
  masters: {},
  natials: {},
  spells: {},
  cardsReady: false,
});

export const CardsContext = React.createContext(defaultValue());

interface Props {
  children: React.ReactNode
}

export function CardsContextProvider({ children }: Props): JSX.Element {
  const [cards, setCards] = useState({
    masters: {},
    natials: {},
    spells: {},
    cardsReady: false,
  });
  useEffect(() => {
    fetch( new Request('/api/cards'))
      .then((response) => response.json())
      .then((cardsMap) => setCards({ ...cardsMap, cardsReady: true }));
  }, [setCards]);
  return (
    <CardsContext.Provider value={cards}>
      {children}
    </CardsContext.Provider>
  );
}
