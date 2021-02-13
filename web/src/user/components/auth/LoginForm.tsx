import React, { SyntheticEvent, useState } from 'react';
import { useHistory } from 'react-router-dom';
import styles from './styles';

interface Props {
  loading: boolean
  setLoading: (b: boolean) => void
}

export default function LoginForm({
  loading,
  setLoading
}: Props): JSX.Element {
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [error, setError] = useState<string>('');
  const history = useHistory();

  const doLogin = async (e: SyntheticEvent) => {
    e.preventDefault();
    if (loading) return;
    setLoading(true);
    const request = new Request('/api/login', {
      method: 'POST',
      body: JSON.stringify({
        name: username,
        password,
      }),
    });
    const response = await fetch(request);
    if (response.ok) {
      history.push('/cards');
    } else {
      setError('Failed to login');
      setLoading(false);
    }
  };
  return (
    <form style={styles.form} onSubmit={doLogin}>
      {error !== '' && <div style={styles.item}>Error: {error}</div>}
      <label htmlFor={'username'} style={styles.item}>Username:</label>
      <input type={'text'} value={username} onChange={(e) => setUsername(e.target.value)} style={styles.item} />
      <label htmlFor={'password'} style={styles.item}>Password:</label>
      <input type={'password'} value={password} onChange={(e) => setPassword(e.target.value)} style={styles.item} />
      <button disabled={loading} type={'submit'} onClick={doLogin} style={styles.item}>Login</button>
    </form>
  );
}
