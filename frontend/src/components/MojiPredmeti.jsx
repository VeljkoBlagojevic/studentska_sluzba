import React from 'react';
import { Link } from 'react-router-dom';

function MojiPredmeti() {
  return (
    <div className='MojiPredmeti'>
        <h1>Moji predmeti</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th scope="row">1</th>
                    <td>Programiranje 1</td>
                    <td>6</td>
                </tr>
                <tr>
                    <th scope="row">2</th>
                    <td>Programiranje 2</td>
                    <td>6</td>
                </tr>
            </tbody>
        </table>
    </div>
  );
}

export default MojiPredmeti