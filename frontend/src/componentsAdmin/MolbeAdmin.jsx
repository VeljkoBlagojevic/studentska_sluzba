import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

function MolbeUObradi() {
    const [data, setData] = useState([]);
    useEffect(() => {
        axios({
            method: 'get',
            url: '/api/v1/molbe',
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
//OMOGUCI DA SE UPISE ODGOVOR NA MOLBU I POTVRDI UNOS
  return (
    <div className='Molba'>
        <h1>Molbe</h1>
        <div class="status">
        </div>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">Tip molbe</th>
                    <th scope="col">Pitanje</th>
                    <th scope="col">Odgovor</th>
                    <th scope="col">Obradi molbu</th>
                </tr>
            </thead>
            <tbody>
              
            </tbody>
        </table>
    </div>
  );
}

export default MolbeUObradi