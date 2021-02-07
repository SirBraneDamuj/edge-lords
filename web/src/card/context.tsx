import React, { useState, useEffect } from 'react';
import { Card } from './types';

interface CardsMap {
  masters: { [key:string]: Card }
  natials: { [key:string]: Card }
  spells: { [key:string]: Card }
}

const defaultValue = (): CardsMap => ({
  masters: {},
  natials: {},
  spells: {},
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
  });
  useEffect(() => {
    fetch( new Request('http://localhost:7000/cards'))
      .then((response) => response.json())
      .then((cardsMap) => setCards(cardsMap));
  }, [setCards]);
  return (
    <CardsContext.Provider value={cards}>
      {children}
    </CardsContext.Provider>
  );
}