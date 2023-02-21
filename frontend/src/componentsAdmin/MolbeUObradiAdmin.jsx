import React, {useEffect, useState} from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

function MolbeUObradi() {
    const [data, setData] = useState([]);
    function obradiMolbu(molba, odgovor){
      console.log(odgovor);
      axios({
        method: 'patch',
        url: '/api/v1/molbe/razresi',
        baseURL: 'http://localhost:8080',
        data: {
          id: molba.id,
          tipMolbe: molba.tipMolbe,
          pitanje: molba.pitanje,
          datumPitanja: molba.datumPitanja,
          odgovor: odgovor,
          student: molba.student
        },
        headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
      }).then((response) => {
        console.log(response);
        console.log(data);
        window.location.href='/molbeIzdate';
      }, (error) => {
        console.log(error);
      });
    }
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
                    <th scope="col">Odgovor</th>
                    <th scope="col">Obradi molbu</th>
                </tr>
            </thead>
            <tbody>
              {data?.map((el, index) => <tr><td>{el.student.ime} {el.student.prezime} {el.student.indeks}</td><td>{el.tipMolbe}</td><td>{el.pitanje}</td><td><input type="text" id={'odgovor'+el.id}/></td>
              <td><button type="button" class="btn btn-outline-danger" onClick={()=>obradiMolbu(el, document.getElementById('odgovor'+el.id).value)}>Obradi molbu</button></td></tr>)}
              
            </tbody>
        </table>
    </div>
  );
}

export default MolbeUObradi