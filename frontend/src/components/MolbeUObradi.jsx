import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

function MolbeUObradi() {
    const [data, setData] = useState([]);
    useEffect(() => {
        axios({
            method: 'get',
            url: '/api/v1/molbe/uobradi',
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
            <form>
            <button class="statusMolbe" formaction="/molbeUObradi">U obradi</button>
            <button class="statusMolbe" formaction="/molbeIzdate">Izdate</button>
            <button class="statusMolbe" formaction="/unesiMolbu">Podnesi molbu</button>
        </form>
        </div>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">Tip molbe</th>
                    <th scope="col">Pitanje</th>
                    <th scope="col">Odgovor</th>
                </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
  );
}

export default MolbeUObradi