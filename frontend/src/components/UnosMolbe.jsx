import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

function UnosMolbe() {
  function podnesiMolbu(){
    axios({
      method: 'post',
      url: '/api/v1/molbe/uobradi',
      baseURL: 'http://localhost:8080',
      data: {
          //sadrzaj: document.getElementById("text").value
          tipMolbe: document.getElementById("tipMolbe").value,
          pitanje: document.getElementById("text").value
          //tip, pitanje
      },
      headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
    }).then((response) => {
      window.location.href = "/molbeUObradi";
    }, (error) => {
      console.log(error);
    });
  }
  return (
    <div className='UnosMolbe'>
      <h1>Unos molbe</h1>
        <h5>Tip molbe:</h5>
        <select name="tipMolbe" id="tipMolbe">
          <option value="PROMENA_PODATAKA_O_STUDENTU">PROMENA_PODATAKA_O_STUDENTU</option>
        </select>
        <div class="form-group">
            <label for="exampleFormControlTextarea1">Obrazlozenje</label>
            <textarea class="form-control" id="text" rows="3"></textarea>
            <br></br>
            <button type="button" class="btn btn-danger" onClick={podnesiMolbu}>Potvrdi</button>
        </div>
    </div>
  );
}

export default UnosMolbe