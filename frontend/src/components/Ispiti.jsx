import React from 'react';
import { Link } from 'react-router-dom';

function Ispiti() {
  return (
    <div className='Ispiti'>
        <h1>Ispiti</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                    <th scope="col">Ocena</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th scope="row">1</th>
                    <td>Projektovanje informacionih sistema</td>
                    <td>6</td>
                    <td>10</td>
                </tr>
                <tr>
                    <th scope="row">2</th>
                    <td>Projektovanje softvera</td>
                    <td>6</td>
                    <td>10</td>
                </tr>
                <tr>
                    <th scope="row">3</th>
                    <td>Internet tehnologije</td>
                    <td>6</td>
                    <td>10</td>
                </tr>
                <tr>
                    <h5>Prosecna ocena:</h5> 
                </tr>
            </tbody>
        </table>
    </div>
  );
}

export default Ispiti