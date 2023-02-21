import React, { useEffect, useState } from 'react'
import axios from 'axios'



function PrijavaIspita(){
    const[data, setData] = useState([]);
    const[dataPrijavljeni, setDataPrijavljeni] = useState([]);
    function update(){
      axios({
        method: 'get',
        url: '/api/v1/prijave',
        baseURL: 'http://localhost:8080',
        data: {},
        headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
      }).then((response) => {
        console.log(response);
        setDataPrijavljeni(response.data)
        console.log(response.data);
      }, (error) => {
        console.log(error);
      }); 
    }
    useEffect(() => {
        axios({
            method: 'get',
            url: '/api/v1/predmeti/slusa',
            baseURL: 'http://localhost:8080',
            data: {},
            headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
          }).then((response) => {
            console.log(response);
            setData(response.data)
            console.log(response.data);
          }, (error) => {
            console.log(error);
          });
          axios({
            method: 'get',
            url: '/api/v1/prijave',
            baseURL: 'http://localhost:8080',
            data: {},
            headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
          }).then((response) => {
            console.log(response);
            setDataPrijavljeni(response.data)
            console.log(response.data);
          }, (error) => {
            console.log(error);
          }); 
    }, [])

    function prijaviIspit(index){
      axios({
        method: 'post',
        url: '/api/v1/prijave',
        baseURL: 'http://localhost:8080',
        data: {
          id: data[index].id,
          naziv: data[index].naziv,
          espb: data[index].espb
          
        },
        headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
      }).then((response) => {
        update();
      }, (error) => {
        console.log(error);
      });
     
    }
    function odjaviIspit(id){
      axios({
        method: 'delete',
        url: '/api/v1/prijave/'+id,
        baseURL: 'http://localhost:8080',
        data: {
        },
        headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
      }).then((response) => {
        update();
      }, (error) => {
        console.log(error);
      });
    }

  return (
    <div className='PrijavaIspita'>
        <h1>Prijava ispita</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                    <th scope="col">Prijavi</th>
                </tr>
            </thead>
            <tbody>
                {data?.map((el, index) => <tr><td>{index+1}</td><td>{el.naziv}</td><td>{el.ESPB}</td><td><button type="button" class="btn btn-outline-danger" onClick={() => prijaviIspit(index)}>Prijavi ispit</button></td></tr>)}
            </tbody>
        </table>
        <h1>Prijavljeni ispiti</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                    <th scope="col">Ponisti prijavu</th>
                </tr>
            </thead>
            <tbody>
                {dataPrijavljeni?.map((el, index) => <tr><td>{index+1}</td><td>{el.naziv}</td><td>{el.ESPB}</td><td><button type="button" class="btn btn-outline-danger" onClick={() => odjaviIspit(el.id)}>X</button></td></tr>)}
            </tbody>
        </table>
    </div>
  )
}

export default PrijavaIspita