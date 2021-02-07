import React, { useState } from 'react';
import LoginForm from './LoginForm';
import RegisterForm from './RegisterForm';
import './Auth.css';

export default function Auth(): JSX.Element {
  const [loading, setLoading] = useState<boolean>(false);
  return (
    <div className={'auth-forms'}>
      <LoginForm loading={loading} setLoading={setLoading} />
      <RegisterForm loading={loading} setLoading={setLoading} />
    </div>
  );
}
