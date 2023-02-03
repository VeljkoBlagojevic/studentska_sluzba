import React from 'react';
import { Link } from 'react-router-dom';

function UnosMolbe() {
  return (
    <div className='UnosMolbe'>
      <h1>Unos molbe</h1>
        <h5>Tip molbe:</h5>
        <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            izaberi tip
        </button>
        <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
            <button class="dropdown-item" type="button">Dodatni ESPB</button>
            <button class="dropdown-item" type="button">Another action</button>
            <button class="dropdown-item" type="button">Something else here</button>
        </div>
        </div>
        <div class="form-group">
            <label for="exampleFormControlTextarea1">Obrazlozenje</label>
            <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
        </div>
    </div>
  );
}

export default UnosMolbe