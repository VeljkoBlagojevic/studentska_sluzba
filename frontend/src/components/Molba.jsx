import React from 'react';
import { Link } from 'react-router-dom';

function Molba() {
  return (
    <div className='Molba'>
        <h1>Molbe</h1>
        <div class="status">
            <form>
            <button class="statusMolbe" formaction="/unesiMolbu">U obradi</button>
            <button class="statusMolbe" formaction="/unesiMolbu">Dopuna</button>
            <button class="statusMolbe" formaction="/unesiMolbu">Izdate</button>
            <button class="statusMolbe" formaction="/unesiMolbu">Podnesi molbu</button>
        </form>
        </div>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">Tip molbe</th>
                    <th scope="col">Podneto</th>
                    <th scope="col">Status molbe</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">Obrazlozenje</th>
                    <th scope="col">Prilog</th>
                    <th scope="col">Detalji</th>
                    <th scope="col">Poslednji komentar</th>
                </tr>
            </thead>
            <tbody>
                
            </tbody>
        </table>
    </div>
  );
}

export default Molba