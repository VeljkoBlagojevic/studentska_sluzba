import React from 'react';
import { Link } from 'react-router-dom';

function Login() {
  return (
    <div className='Login'>
      <h1>Login</h1>
        <form>
  <div class="form-outline mb-4">
    <input type="email" id="form2Example1" class="form-control" />
    <label class="form-label" for="form2Example1">Korisnicko ime</label>
  </div>

  <div class="form-outline mb-4">
    <input type="password" id="form2Example2" class="form-control" />
    <label class="form-label" for="form2Example2">Lozinka</label>
  </div>


  <button type="button" class="btn btn-outline-danger">Prijavi se</button>

  
</form>
    </div>
  );
}

export default Login