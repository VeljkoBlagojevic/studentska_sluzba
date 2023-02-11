import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';




class UpisiOcenu extends React.Component {

  state = {
    data: [],
    dataPrijavljeni: []
  }

  builder(){
    this.forceUpdateHandler = this.forceUpdateHandler.bind(this);
  };
  
  forceUpdateHandler(){
    this.forceUpdate();
  };
  
  componentDidMount() {
    //POSLATI AXIOS ZAHTEV ZA SVE PRIJAVE
      axios({
        method: 'get',
        url: '/api/v1/prijave',
        baseURL: 'http://localhost:8080',
        data: {},
        headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
      }).then((response) => {
        console.log(response);
        this.setState({dataPrijavljeni: response.data})
        console.log(this.state.data);
      }, (error) => {
        console.log(error);
      });
  }
  render(){
  //OMOGUCI DA SE UPISE OCENA I POTVRDI UNOS
  return (
    <div className='PrijavaIspita'>
        <h1>Prijave</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">Student</th>
                    <th scope="col">Ocena</th>
                    <th scope="col">Potvrdi</th>
                </tr>
            </thead>
            <tbody>
                {this.state?.data.map((el, index) => <tr><td>{index+1}</td><td>{el.naziv}</td><td>{el.ESPB}</td><td><button type="button" class="btn btn-outline-danger" onClick={() => this.prijaviIspit(index)}>Prijavi ispit</button></td></tr>)}
            </tbody>
        </table>
    </div>
  );
}}

export default UpisiOcenu