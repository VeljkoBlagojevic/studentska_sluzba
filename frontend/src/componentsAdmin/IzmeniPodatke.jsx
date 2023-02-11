import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

function IzmeniPodatke() {
    const [data, setData] = useState([]);
    useEffect(() => {
        axios({
            method: 'get',
            url: '/api/v1/studenti',
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

      function Izmeni(id){
        localStorage.setItem('student', id);
        window.location.href = "/podaciOStudentu";
      };

  return (
    <div className='Molba'>
        <h1>Molbe</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Student</th>
                    <th scope="col">Indeks</th>
                    <th scope="col">Izmeni</th>
                </tr>
            </thead>
            <tbody>
                {data.map((el) => <tr><td>{el.id}</td><td>{el.ime}</td><td>{el.indeks}</td><td><button type="button" class="btn btn-outline-danger" onClick={() => Izmeni(el.id)}>Izmeni</button></td></tr>)}
            </tbody>
        </table>
    </div>
  );
}

export default IzmeniPodatke