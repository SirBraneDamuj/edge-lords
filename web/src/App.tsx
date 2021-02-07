import React from 'react';
import './card/components/AllCardsList';
import './App.css';
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

function App(): JSX.Element {
  return (
    <>
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
            <Route path={'/decks'}>
              <DeckList />
            </Route>
            <Route path={'/'}>
              <Redirect to={'/login'} />
            </Route>
          </Switch>
        </Router>
      </CardsContextProvider>
    </>
  );
}

export default App;
