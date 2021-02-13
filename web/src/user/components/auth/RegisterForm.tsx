import React, { SyntheticEvent, useState } from 'react';
import styles from './styles';

interface Props {
  loading: boolean
  setLoading: (b: boolean) => void
}

export default function RegisterForm({
  loading,
  setLoading
}: Props): JSX.Element {
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [passwordConfirm, setPasswordConfirm] = useState<string>('');
  const [error, setError] = useState<string>('');
  const [success, setSuccess] = useState<string>('');

  const doRegister = async (e: SyntheticEvent) => {
    e.preventDefault();
    if (loading) return;
    if (password !== passwordConfirm) { setError('Passwords don\'t match'); }
    setLoading(true);
    const request = new Request('/api/users', {
      method: 'POST',
      body: JSON.stringify({
        name: username,
        password,
      }),
    });
    const response = await fetch(request);
    if (response.ok) {
      setError('');
      setSuccess('User created, please login');
    } else {
      setError('Failed to login');
    }
    setLoading(false);
  };

  return (
    <>
      {error !== '' && <div>Error: {error}</div>}
      {success !== '' && <div>OK: {success}</div>}
      <form onSubmit={doRegister} style={styles.form}>
        <label htmlFor={'username'} style={styles.item}>Username:</label>
        <input name={'username'} type={'text'} value={username} onChange={(e) => setUsername(e.target.value)} style={styles.item} />
        <label htmlFor={'password'} style={styles.item}>Password:</label>
        <input name={'password'} type={'password'} value={password} onChange={(e) => setPassword(e.target.value)} style={styles.item} />
        <label htmlFor={'passwordConfirm'} style={styles.item}>Confirm:</label>
        <input name={'passwordConfirm'} type={'password'} value={passwordConfirm} onChange={(e) => setPasswordConfirm(e.target.value)} style={styles.item} />
        <button disabled={loading} type={'submit'} onClick={doRegister} style={styles.item}>Register</button>
      </form>
    </>
  );
}
