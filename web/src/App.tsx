import React, { useEffect, useState } from 'react';
import './card/components/CardList';
import './App.css';
import CardList from './card/components/CardList';
import { CardsContextProvider } from './card/context';

function App(): JSX.Element {
  return (
    <CardsContextProvider>
      <CardList />
    </CardsContextProvider>
  );
}

export default App;
