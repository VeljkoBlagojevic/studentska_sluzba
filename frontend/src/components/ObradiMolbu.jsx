import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

function UnosMolbe() {
  function odgovor(){
    alert('Ubaci odobravanje molbe');
  }
  return (
    <div className='UnosMolbe'>
      <h1>Unos molbe</h1>
        <h5>Tip molbe:</h5>
        <div class="form-group">
            <label for="exampleFormControlTextarea1">Odgovor</label>
            <textarea class="form-control" id="text" rows="3"></textarea>
            <br></br>
            <button type="button" class="btn btn-danger" onClick={odgovor}>Potvrdi</button>
        </div>
    </div>
  );
}

export default UnosMolbe