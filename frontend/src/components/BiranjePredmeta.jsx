import React from 'react';
import { Link } from 'react-router-dom';

function BiranjePredmeta() {
  return (
    <div className='BiranjePredmeta'>
        <h1>Biranje predmeta</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope='col'>Semestar</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                    <th scope="col">Dodaj</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th scope="row">1</th>
                    <td>2</td>
                    <td>Programiranje 1</td>
                    <td>6</td>
                    <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault"></input>
                </tr>
                <tr>
                    <th scope="row">2</th>
                    <td>8</td>
                    <td>Inteligentni sistemi</td>
                    <td>6</td>
                    <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault"></input>
                </tr>
            </tbody>
        </table>
        <button type="button" class="btn btn-danger">Potvrdi</button>
    </div>
  );
}

export default BiranjePredmeta