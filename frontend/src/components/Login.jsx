import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import urlBase from '../App';
import axios from 'axios';

function Login() {
  const [username, setUsername] = useState('');
  const [passwrod, setPassword] = useState('');
  
  const handleChangeUsername = (event) => {
    setUsername(event.target.value);
  };

  const handleChangePassword = (event) => {
    setPassword(event.target.value);
  };
  return (
    <div className='Login'>
      <h1>Login</h1>
        <form>
          <div class="form-outline mb-4">
            <input type="text" id="username" class="form-control" name='username' value={username} onChange={handleChangeUsername}/>
            <label class="form-label" for="form2Example1">Korisnicko ime</label>
          </div>

          <div class="form-outline mb-4">
            <input type="password" id="form2Example2" class="form-control" name='password' value={passwrod} onChange={handleChangePassword}/>
            <label class="form-label" for="form2Example2">Lozinka</label>
          </div>


          <button type="button" class="btn btn-outline-danger" onClick={() => {
            axios({
              method: 'post',
              url: '/api/v1/auth/authenticate',
              baseURL: 'http://localhost:8080',
              data: {
                username: username,
                password: passwrod
              }
            }).then((response) => {
              console.log(response);
              localStorage.setItem('token', response.data.token);
            }, (error) => {
              console.log(error);
            });
          }}>Prijavi se</button>

        </form>
    </div>
  );
}

export default Login