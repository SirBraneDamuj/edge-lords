import React from 'react';
import {
  Link,
} from 'react-router-dom';
import LogoutButton from './user/components/auth/LogoutButton';
import './Header.css';

export default function Header(): JSX.Element {
  return (
    <nav>
      <h1>Edge Lords</h1>
      <div>
        <Link to={'/cards'}>Cards</Link>
      </div>
      <div>
        <Link to={'/decks'}>Decks</Link>
      </div>
      <div>
        <LogoutButton />
      </div>
    </nav>
  );
}