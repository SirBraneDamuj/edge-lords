import React, { useState } from 'react';
import LoginForm from './LoginForm';
import RegisterForm from './RegisterForm';

export default function Auth(): JSX.Element {
  const [loading, setLoading] = useState<boolean>(false);
  const styles = {
    display: 'flex',
    flexDirection: 'row' as const,
  };
  return (
    <div style={styles}>
      <LoginForm loading={loading} setLoading={setLoading} />
      <RegisterForm loading={loading} setLoading={setLoading} />
    </div>
  );
}
