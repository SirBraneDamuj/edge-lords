import React from 'react';
import {
  BrowserRouter as Router,
  Redirect, Route, Switch
} from 'react-router-dom';
import './card/components/AllCardsList';
import AllCardsList from './card/components/AllCardsList';
import { CardsContextProvider } from './card/context';
import { EditDeckBuilder, NewDeckBuilder } from './deck/components/builder/DeckBuilder';
import DeckDetailForId from './deck/components/detail/DeckDetail';
import DeckList from './deck/components/list/DeckList';
import GameDetailForGameIdParam from './game/components/detail/GameDetail';
import GameList from './game/components/list/GameList';
import NewGame from './game/components/new/NewGame';
import Header from './Header';
import Auth from './user/components/auth/Auth';

function App(): JSX.Element {
  return (
    <div style={{ maxWidth: 960, margin: 'auto' }}>
      <CardsContextProvider>
        <Router>
          <Header />
          <Switch>
            <Route path={'/login'}>
              <Auth />
            </Route>
            <Route path={'/cards'}>
              <AllCardsList />
            </Route>
            <Route path={'/decks/new'}>
              <NewDeckBuilder />
            </Route>
            <Route path={'/decks/edit/:deckId'}>
              <EditDeckBuilder />
            </Route>
            <Route path={'/decks/:deckId'}>
              <DeckDetailForId />
            </Route>
            <Route path={'/decks'}>
              <DeckList />
            </Route>
            <Route path={'/games/new'}>
              <NewGame />
            </Route>
            <Route path={'/games/:gameId'}>
              <GameDetailForGameIdParam />
            </Route>
            <Route path={'/games'}>
              <GameList />
            </Route>
            <Route path={'/'}>
              <Redirect to={'/decks'} />
            </Route>
          </Switch>
        </Router>
      </CardsContextProvider>
    </div>
  );
}

export default App;
