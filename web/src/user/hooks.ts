import React, { useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import Cookies from 'js-cookie';

export function useAuth(): void {
  const history = useHistory();

  useEffect(() => {
    const cookie = Cookies.get('sid');

    if (!cookie) {
      history.push('/login');
    }
  }, [history]);
}
