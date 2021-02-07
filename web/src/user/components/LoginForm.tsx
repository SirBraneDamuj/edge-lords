import React, { SyntheticEvent, useState } from 'react';
import { useHistory } from 'react-router-dom';
import './LoginForm.css';

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
    const request = new Request('http://localhost:7000/login', {
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
    <>
      {error !== '' && <div>Error: {error}</div>}
      <form className={'login-form'} onSubmit={doLogin}>
        <label htmlFor={'username'}>Username:</label>
        <input type={'text'} value={username} onChange={(e) => setUsername(e.target.value)} />
        <label htmlFor={'password'}>Password:</label>
        <input type={'password'} value={password} onChange={(e) => setPassword(e.target.value)} />
        <button disabled={loading} type={'submit'} onClick={doLogin}>Login</button>
      </form>
    </>
  );
}
