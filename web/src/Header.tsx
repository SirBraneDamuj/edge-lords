import React from 'react';
import {
  Link,
} from 'react-router-dom';
import LogoutButton from './user/components/auth/LogoutButton';

export default function Header(): JSX.Element {
  const styles = {
    nav: {
      display: 'flex',
      alignItems: 'baseline',
    },
    item: {
      margin: '1.5rem',
    }
  };
  return (
    <nav style={styles.nav}>
      <h1>Edge Lords</h1>
      <div style={styles.item}>
        <Link to={'/cards'}>Cards</Link>
      </div>
      <div style={styles.item}>
        <Link to={'/decks'}>Decks</Link>
      </div>
      <div style={styles.item}>
        <LogoutButton />
      </div>
    </nav>
  );
}
