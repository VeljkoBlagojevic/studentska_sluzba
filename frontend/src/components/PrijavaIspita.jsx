import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';


class PrijavaIspita extends React.Component {

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
    axios({
        method: 'get',
        url: '/api/v1/predmeti/slusa',
        baseURL: 'http://localhost:8080',
        data: {},
        headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
      }).then((response) => {
        console.log(response);
        this.setState({data: response.data})
        console.log(this.state.data);
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
        this.setState({dataPrijavljeni: response.data})
        console.log(this.state.data);
      }, (error) => {
        console.log(error);
      });
  }
  prijaviIspit(index){
    axios({
      method: 'post',
      url: '/api/v1/prijave',
      baseURL: 'http://localhost:8080',
      data: {
        id: this.state.data[index].id,
        naziv: this.state.data[index].naziv,
        espb: this.state.data[index].espb
        
      },
      headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
    }).then((response) => {
    }, (error) => {
      console.log(error);
    });
    this.forceUpdateHandler();
  }
  odjaviIspit(id){
    axios({
      method: 'delete',
      url: '/api/v1/prijave/'+id,
      baseURL: 'http://localhost:8080',
      data: {
      },
      headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
    }).then((response) => {
    }, (error) => {
      console.log(error);
    });
    this.forceUpdateHandler();
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
                {this.state?.data.map((el, index) => <tr><td>{index+1}</td><td>{el.naziv}</td><td>{el.ESPB}</td><td><button type="button" class="btn btn-outline-danger" onClick={() => this.prijaviIspit(index)}>Prijavi ispit</button></td></tr>)}
            </tbody>
        </table>
        <h1>Prijavljeni ispiti</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                </tr>
            </thead>
            <tbody>
                {this.state?.dataPrijavljeni.map((el, index) => <tr><td>{index+1}</td><td>{el.naziv}</td><td>{el.ESPB}</td><td><button type="button" class="btn btn-outline-danger" onClick={() => this.odjaviIspit(el.id)}>X</button></td></tr>)}
            </tbody>
        </table>
    </div>
  );
}}

export default PrijavaIspita