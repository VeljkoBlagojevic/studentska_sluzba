import React from 'react';
import { Link } from 'react-router-dom';

function Student() {
  return (
    <div className='Student'>
        <h1>Podaci o studentu</h1>
        <table class="table">
            <tbody>
                <tr>
                    <th>Broj indeksa</th>
                    <td>62/19</td>
                </tr>
                <tr>
                    <th>Ime i prezime</th>
                    <td>Nikola Vujcic</td>
                </tr>
                <tr>
                    <th>Pol</th>
                    <td>Muski</td>
                </tr>
                <tr>
                    <th>Mesto rodjenja</th>
                    <td>Petrovac na Mlavi</td>
                </tr>
                <tr>
                    <th>Email</th>
                    <td>nikola.vujcic.001@gmail.com</td>
                </tr>
                <tr>
                    <th>Stanje na racunu</th>
                    <td>150</td>
                </tr>
            </tbody>
        </table>
    </div>
  );
}

export default Student