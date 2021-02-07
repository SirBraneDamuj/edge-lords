import React from 'react';
import './card/components/CardList';
import './App.css';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from 'react-router-dom';
import { CardsContextProvider } from './card/context';
import CardList from './card/components/CardList';
import Header from './Header';
import Auth from './user/components/auth/Auth';

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
              <CardList />
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
