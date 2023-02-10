import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import Paginacija from './Paginacija';

class IspitiNeuspesno extends React.Component {
    constructor(){
    super();
    this.state = {
      data: [],
      trenutnaStrana:1,
    }
    this.setTrenutnaStranica = this.setTrenutnaStranica.bind(this)
  }
    state = {
    }

    indeksPoslednjegPredmeta = 4;
    indeksPrvogPredmeta = 1;
    trenutniPredmeti = [];
    
    elementiPoStrani= 4;

  setTrenutnaStranica(brojStranice) {
        this.state.trenutnaStrana = brojStranice;
        this.forceUpdate();
    }

    componentDidMount() {
      axios({
          method: 'get',
          url: '/api/v1/polaganja/neuspesna',
          baseURL: 'http://localhost:8080',
          data: {},
          headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
        }).then((response) => {
          console.log(response);
          this.setState({data: response.data});
          console.log(this.state.data);
        }, (error) => {
          console.log(error);
        });
    }
    
    render(){
      this.indeksPoslednjegPredmeta = this.state.trenutnaStrana * this.elementiPoStrani;
      this.indeksPrvogPredmeta = this.indeksPoslednjegPredmeta - this.elementiPoStrani;
      this.trenutniPredmeti = this.state.data.slice(this.indeksPrvogPredmeta, this.indeksPoslednjegPredmeta);
  return (
    <div className='Ispiti'>
        <h1>Ispiti</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                    <th scope="col">Ocena</th>
                    <th scope="col">Datum</th>
                </tr>
            </thead>
            <tbody>
                    {this.trenutniPredmeti.map((el, index) => <tr><td>{index+1}</td><td>{el.predmet?.naziv}</td><td>{el.predmet?.ESPB}</td><td>{el.ocena}</td><td>{el.datum}</td><td></td></tr>)}
            </tbody>
        </table>
        <Paginacija ukupniPredmeti = {this.state.data.length} 
        elementiPoStrani = {this.elementiPoStrani}
        setTrenutnaStranica = {this.setTrenutnaStranica}/>
    </div>
  );
}
}

export default IspitiNeuspesno