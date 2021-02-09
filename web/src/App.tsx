import React from 'react';
import './card/components/AllCardsList';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from 'react-router-dom';
import { CardsContextProvider } from './card/context';
import AllCardsList from './card/components/AllCardsList';
import Header from './Header';
import Auth from './user/components/auth/Auth';
import DeckList from './deck/components/list/DeckList';
import DeckDetailForId from './deck/components/detail/DeckDetail';
import DeckBuilder from './deck/components/builder/DeckBuilder';

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
              <DeckBuilder />
            </Route>
            <Route path={'/decks/:deckId'}>
              <DeckDetailForId />
            </Route>
            <Route path={'/decks'}>
              <DeckList />
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
