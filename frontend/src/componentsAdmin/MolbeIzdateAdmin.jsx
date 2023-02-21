import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

function MolbeIzdateAdmin() {
    const [data, setData] = useState([]);
    useEffect(() => {
        axios({
            method: 'get',
            url: '/api/v1/molbe/razresene',
            baseURL: 'http://localhost:8080',
            data: {},
            headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
          }).then((response) => {
            console.log(response);
            setData(response.data);
            console.log(data);
          }, (error) => {
            console.log(error);
          });
      }, []);
  return (
    <div className='Molba'>
        <h1>Molbe</h1>
        <div class="status">
        </div>
        <div class="status">
            <form>
            <button class="statusMolbe" formaction="/molbeUObradi">U obradi</button>
            <button class="statusMolbe" formaction="/molbeIzdate">Razresene</button>
          </form>
        </div>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">Student</th>
                    <th scope="col">Tip molbe</th>
                    <th scope="col">Pitanje</th>
                    <th scope="col">Datum pitanja</th>
                    <th scope="col">Odgovor</th>
                    <th scope="col">Datum odgovora</th>
                </tr>
            </thead>
            <tbody>
              {data?.map((el, index) => <tr><td>{el.student.ime} {el.student.prezime} {el.student.indeks}</td><td>{el.tipMolbe}</td><td>{el.pitanje}</td><td>{el.datumPitanja}</td><td>{el.odgovor}</td><td>{el.datumOdgovora}</td></tr>)}
            </tbody>
        </table>
    </div>
  );
}

export default MolbeIzdateAdmin