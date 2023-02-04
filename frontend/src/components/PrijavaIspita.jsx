import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

function PrijavaIspita() {
    
    axios({
        method: 'get',
        url: '/api/v1/predmeti/slusa',
        baseURL: 'http://localhost:8080',
        headers: {Authorization: 'fsdf'}
      }).then((response) => {
        console.log(response);
      }, (error) => {
        console.log(error);
      });
  return (
    <div className='PrijavaIspita'>
        <h1>Prijava ispita</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                    <th scope="col">Prijavi</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th scope="row">1</th>
                    <td>Programiranje 1</td>
                    <td>6</td>
                    <td><button type="button" class="btn btn-outline-danger">Prijavi ispit</button></td>
                </tr>
                <tr>
                    <th scope="row">2</th>
                    <td>Programiranje 2</td>
                    <td>6</td>
                    <td><button type="button" class="btn btn-outline-danger">Prijavi ispit</button></td>
                </tr>
            </tbody>
        </table>
    </div>
  );
}

export default PrijavaIspita