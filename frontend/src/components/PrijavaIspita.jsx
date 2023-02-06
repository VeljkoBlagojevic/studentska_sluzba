import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';




class PrijavaIspita extends React.Component {

  state = {
    data: []
  }
  
  componentDidMount() {
    axios({
        method: 'get',
        url: '/api/v1/predmeti/slusa1',
        baseURL: 'http://localhost:8080',
        data: {},
        headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
      }).then((response) => {
        console.log(response);
        this.setState({data: response.data})
        console.log(this.state.data);
        //var newArray=[];
        //var test=JSON.parse(response.data);
        //for(const key in response.data){
            //console.log(`${key} : ${response.data[key].naziv}`)
            //data.push(`${key} : ${response.data[key].naziv}`);
        //}
        //setArr(newArray);
        //arr=newArray;
        //alert(arr)
        //setData(arr)
      }, (error) => {
        console.log(error);
      });
  }
    
  render(){

  
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
                {this.state?.data.map((el, index) => <tr><td>{index+1}</td><td>{el.naziv}</td><td>{el.espb}</td><td><button type="button" class="btn btn-outline-danger">Prijavi ispit</button></td></tr>)}
            </tbody>
        </table>
    </div>
  );
}}

export default PrijavaIspita