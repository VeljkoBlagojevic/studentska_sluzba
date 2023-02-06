import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import urlBase from '../App';
import axios from 'axios';

function Login() {
  const [username, setUsername] = useState('');
  const [passwrod, setPassword] = useState('');
  const [poruka, setPoruka] = useState('');
  
  const handleChangeUsername = (event) => {
    setUsername(event.target.value);
  };

  const handleChangePassword = (event) => {
    setPassword(event.target.value);
  };

  localStorage.clear();
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
              window.location.href = "/Obavestenja";
            }, (error) => {
              console.log(error);
              setPoruka("Neispravno korisnicko ime ili sifra!");
            });
          }}>Prijavi se</button>

        </form>
        <br></br>
         <div class="poruka">
            <h4><b>{poruka}</b></h4>
          </div>
    </div>
  );
}

export default Login