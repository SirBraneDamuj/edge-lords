import React, { SyntheticEvent, useState } from 'react';
import { useHistory } from 'react-router-dom';

export default function LogoutButton() {
  const [loading, setLoading] = useState<boolean>(false);
  const history = useHistory();

  const doLogout = async (e: SyntheticEvent) => {
    e.preventDefault();
    setLoading(true);
    const request = new Request('http://localhost:7000/logout', {
      method: 'POST',
    });
    await fetch(request);
    setLoading(false);
    history.push('/login');
  };

  return (
    <button disabled={loading} onClick={doLogout}>Logout</button>
  );
}
