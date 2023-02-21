
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

function convert(str){

var arr = str.slice(11, -1).split(', ');

var obj = new Object();

for (var el of arr) {
  	var tmp = el.split('=');
  	if (tmp.length != 2) continue;
  	obj[tmp[0]] = tmp[1];
}

return obj;
}

const PrikazPrijava = () => {

    const [data, setData] = useState([]);
    const [predmeti, setPredmeti] = useState([]);

    useEffect(() => {
        axios({
            method: 'get',
            url: '/api/v1/prijave/admin',
            baseURL: 'http://localhost:8080',
            data: {},
            headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
          }).then((response) => {
            console.log(response.data)
            setData(response.data);
            var array = [];
            var array2 = [];
            //alert(JSON.stringify(response.data));

            Object.keys(response.data).map(studentMapa => {
              array.push(convert(studentMapa));
              array2.push(response.data[studentMapa]);
              console.log(response.data[studentMapa]);
              //alert(JSON.stringify(response.data[studentMapa]))
            })
            setData(array);
            setPredmeti(array2);
            //alert(JSON.stringify(array2))
            console.log(array2)
          }, (error) => {
            console.log(error);
          });
      }, []);

  return (
    <div className='PrijavaIspita'>
        {console.log(data)}
        <h1>Prijave</h1>
        
        {Object.keys(data).map(studentMapa => {
          return <>
        <h2>{data[studentMapa]['indeks']} {data[studentMapa]['ime']} {data[studentMapa]['prezime']}</h2>
            <table class="table">
            
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                </tr>
            </thead>
            <tbody>
              {Object.keys(predmeti[studentMapa]).map(element => {
                return <>
                <tr>
                  <td>
                    {element}
                  </td>
                  <td>
                    {predmeti[studentMapa][element]['naziv']}
                  </td>
                  <td>
                    {predmeti[studentMapa][element]['ESPB']}
                  </td>
              </tr></>
              })}
              
            </tbody>
        </table></>
        })}
    </div>
  )
}

export default PrikazPrijava