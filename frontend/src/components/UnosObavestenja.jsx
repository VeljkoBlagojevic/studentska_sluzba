import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';



function UnosObavestenja() {
    function potvrdiUnos(){
        axios({
            method: 'post',
            url: '/api/v1/obavestenja',
            baseURL: 'http://localhost:8080',
            data: {
                sadrzaj: document.getElementById("text").value
            },
            headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
          }).then((response) => {
          }, (error) => {
            console.log(error);
          });
        
       window.location.href = "/obavestenja"
    }
    return (
        <div className='UnosMolbe'>
        <h1>Unos obavestenja</h1>
            <div class="form-group">
                <textarea class="form-control" id="text" rows="3"></textarea>
            </div>
            <br></br>
            <button type="button" class="btn btn-outline-danger" onClick={potvrdiUnos}>Potvrdi</button>
        </div>
    );
}

export default UnosObavestenja